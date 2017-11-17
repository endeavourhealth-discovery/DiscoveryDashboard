package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.managers.MessageProcessor;
import org.endeavourhealth.adastrareceiver.api.managers.MessageSender;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Path("/dashboard")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver dashboard")
public class DashboardEndpoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.messageCount")
    @Path("/messageCount")
    @ApiOperation(value = "returns the total number of messages received")
    public Response getMessageCount(@Context SecurityContext sc,
                                    @ApiParam(value = "message Status") @QueryParam("status") byte status) throws Exception {
        System.out.println("Count");

        return getMessageCount(status);
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.resendMessages")
    @Path("/resendMessages")
    @ApiOperation(value = "Resend specific messages")
    public Response resendMessages(@Context SecurityContext sc,
                                   @ApiParam(value = "Message Id to resend") @QueryParam("messageId") Long messageId,
                                   @ApiParam(value = "string to determine whether to delete messages before or after specified") @QueryParam("mode") String mode) throws Exception {

        return resendMessage(messageId, mode);
    }

    private Response getMessageCount(byte status) throws Exception {

        Long messageCount = MessageStoreEntity.getTotalNumberOfMessages(status);
        return Response
                .ok(messageCount)
                .build();
    }

    private Response resendMessage(Long messageId, String mode) throws Exception {
        String result = "Nothing changed";

        if (messageId != null && mode != null) {
            switch (mode) {
                case "single":
                    result = MessageStoreEntity.resendSingleMessage(messageId);
                    break;
                case "error":
                    if (messageId.equals(0L))  //extra fail safe check
                        result = MessageStoreEntity.resendErrorMessages();
                    break;
                case "before":
                    result = MessageStoreEntity.resendMessagesBefore(messageId);
                    break;
                case "after":
                    result = MessageStoreEntity.resendMessagesAfter(messageId);
                    break;
                case "all":
                    if (messageId.equals(0L))  //extra fail safe check
                        result = MessageStoreEntity.resendAllMessages();
                    break;
                default:
                    break;
            }
        }

        return Response
                .ok()
                .entity(result)
                .build();
    }

}
