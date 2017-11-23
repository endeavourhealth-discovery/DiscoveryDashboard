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
    private static Instant lastSend = Instant.now();
    private static String messagingApiURL = null;
    private static boolean useKeycloak;
    private static Integer batchSize;

    public static Instant getRunDate() {
        return lastSend;
    }

    public static String getMessagingApiURL() {
        return messagingApiURL;
    }

    public static void setMessagingApiURL(String messagingApiURL) {
        MessageSender.messagingApiURL = messagingApiURL;
    }

    public static boolean isUseKeycloak() {
        return useKeycloak;
    }

    public static void setUseKeycloak(boolean useKeycloak) {
        MessageSender.useKeycloak = useKeycloak;
    }

    public static Integer getBatchSize() {
        return batchSize;
    }

    public static void setBatchSize(Integer batchSize) {
        MessageSender.batchSize = batchSize;
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

        List<MessageStoreEntity> messages = MessageStoreEntity.getEarliestUnsentMessages(batchSize);

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


        //edsSenderResponse = EdsSender.notifyEds(messagingApiURL, useKeycloak, outboundMessage);

        //MessageStoreEntity.updateMessageStatus(message.getId(), MessageStatus.PROCESSED.getMessageStatus());

        return true;
    }
}
