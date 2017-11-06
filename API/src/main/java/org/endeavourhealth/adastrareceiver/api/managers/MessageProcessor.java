package org.endeavourhealth.adastrareceiver.api.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.common.eds.EdsSender;
import org.endeavourhealth.common.eds.EdsSenderHttpErrorResponseException;
import org.endeavourhealth.common.eds.EdsSenderResponse;
import sun.plugin2.message.Message;
import sun.reflect.annotation.ExceptionProxy;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageProcessor {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private static Instant lastSend = Instant.now();
    private Future<?> future = scheduler.scheduleAtFixedRate(messageChecker, 0L, 5L, TimeUnit.SECONDS);
    private static JsonNode jsonConfig = null;

    public void startProcessor() {
        future = scheduler.scheduleAtFixedRate(messageChecker, 0L, 5L, TimeUnit.SECONDS);
    }

    public void stopProcessor() {
        future.cancel(false);
    }

    public boolean processorStatus() {
        return !scheduler.isTerminated();
    }

    public Instant getLastSentTime() {
        return lastSend;
    }

    private static Runnable messageChecker = new Runnable() {
        @Override
        public void run() {
            boolean success = true;
            try {
                boolean messagesToProcess = checkForNewMessages();
            } catch (Exception e) {
                System.out.println("error occurred");

            }
        }
    };


    public static boolean checkForNewMessages() throws Exception {
        List<MessageStoreEntity> messages = MessageStoreEntity.getEarliestUnsendMessages();

        for (MessageStoreEntity message: messages) {
            System.out.println(message.getId());
            sendMessageToAPI(message);
        }
        return true;
    }

    private static boolean sendMessageToAPI(MessageStoreEntity message) throws IOException, EdsSenderHttpErrorResponseException, Exception {
        lastSend = Instant.now();
        UUID messageId = UUID.randomUUID();
        UUID orgId = UUID.randomUUID();
        String outboundMessage = null;

        EdsSenderResponse edsSenderResponse = null;

        outboundMessage = EdsSender.buildEnvelope(messageId, orgId.toString(), "Adastra", "1.0", message.getMessagePayload());

        JsonNode config = getConfig();
        String edsURL = config.get("eds_url").asText();
        Boolean useKeycloak = config.get("useKeycloak").asBoolean();

        edsSenderResponse = EdsSender.notifyEds(edsURL, useKeycloak, outboundMessage);

        MessageStoreEntity.updateMessageStatus(message.getId(), MessageStatus.PROCESSED.getMessageStatus());

        return true;
    }

    private static JsonNode getConfig() throws IOException {
        if (jsonConfig == null) {
            jsonConfig = ConfigManager.getConfigurationAsJson("messagingAPI");
        }

        return jsonConfig;
    }

    public void clearConfigCache() {
        jsonConfig = null;
    }
}
