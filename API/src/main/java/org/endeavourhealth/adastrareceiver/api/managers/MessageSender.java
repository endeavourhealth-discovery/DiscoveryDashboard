package org.endeavourhealth.adastrareceiver.api.managers;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.common.eds.EdsSender;
import org.endeavourhealth.common.eds.EdsSenderHttpErrorResponseException;
import org.endeavourhealth.common.eds.EdsSenderResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MessageSender implements Runnable {
    private JsonNode jsonConfig = null;
    private static Instant lastSend = Instant.now();

    public static Instant getRunDate() {
        System.out.println(lastSend);
        return lastSend;
    }


    @Override
    public void run() {
        boolean success = true;
        lastSend = Instant.now();
        try {
            boolean messagesToProcess = checkForNewMessages();
        } catch (Exception e) {
            System.out.println("error occurred");

        }
    }

    private boolean checkForNewMessages() throws Exception {
        List<MessageStoreEntity> messages = MessageStoreEntity.getEarliestUnsentMessages();

        for (MessageStoreEntity message: messages) {
            System.out.println(message.getId());
            sendMessageToAPI(message);
        }
        return true;
    }

    private boolean sendMessageToAPI(MessageStoreEntity message) throws IOException, EdsSenderHttpErrorResponseException, Exception {
        lastSend = Instant.now();
        UUID messageId = UUID.randomUUID();
        UUID orgId = UUID.randomUUID();
        String outboundMessage = null;

        EdsSenderResponse edsSenderResponse = null;

        outboundMessage = EdsSender.buildEnvelope(messageId, orgId.toString(), "Adastra", "1.0", message.getMessagePayload());

        JsonNode config = getConfig();
        String edsURL = config.get("eds_url").asText();
        Boolean useKeycloak = config.get("useKeycloak").asBoolean();

        //edsSenderResponse = EdsSender.notifyEds(edsURL, useKeycloak, outboundMessage);

        //MessageStoreEntity.updateMessageStatus(message.getId(), MessageStatus.PROCESSED.getMessageStatus());

        return true;
    }

    private JsonNode getConfig() throws IOException {
        if (jsonConfig == null) {
            jsonConfig = ConfigManager.getConfigurationAsJson("messagingAPI");
        }

        return jsonConfig;
    }
}
