package org.endeavourhealth.adastrareceiver.api.managers;

import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import sun.reflect.annotation.ExceptionProxy;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity.getEarliestUnsendMessages;

public class MessageProcessor {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private static Instant lastSend = Instant.now();
    private Future<?> future = scheduler.scheduleAtFixedRate(messageChecker, 0L, 5L, TimeUnit.SECONDS);

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
                if (messagesToProcess) {
                    success = sendMessageToAPI();
                } else {
                    scheduler.shutdown();
                }
            } catch (Exception e) {
                System.out.println("error occurred");

            }
        }
    };


    public static boolean checkForNewMessages() throws Exception {
        List<MessageStoreEntity> messages = MessageStoreEntity.getEarliestUnsendMessages();

        for (MessageStoreEntity message: messages) {
            System.out.println(message.getId());
        }
        return true;
    }

    private static boolean sendMessageToAPI() {
        lastSend = Instant.now();
        return true;
    }
}
