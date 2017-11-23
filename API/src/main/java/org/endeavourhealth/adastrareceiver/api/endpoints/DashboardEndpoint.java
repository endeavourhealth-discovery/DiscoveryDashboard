package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.adastrareceiver.api.json.JsonDashboardStatistics;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/dashboard")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver dashboard")
public class DashboardEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getDashboardStatistics")
    @Path("/getDashboardStatistics")
    @ApiOperation(value = "Gets the dashboard Statistics")
    public Response getMessageCount(@Context SecurityContext sc) throws Exception {

        return getDashboardStatistics();
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

    private Response getDashboardStatistics() throws Exception {

        JsonDashboardStatistics statistics = new JsonDashboardStatistics();
        statistics.setTotalMessageCount(MessageStoreEntity.getTotalNumberOfMessages(MessageStatus.ALL.getMessageStatus()));
        statistics.setReceivedMessageCount(MessageStoreEntity.getTotalNumberOfMessages(MessageStatus.RECEIVED.getMessageStatus()));
        statistics.setSentMessageCount(MessageStoreEntity.getTotalNumberOfMessages(MessageStatus.PROCESSED.getMessageStatus()));
        statistics.setErrorMessageCount(MessageStoreEntity.getTotalNumberOfMessages(MessageStatus.ERROR.getMessageStatus()));

        return Response
                .ok(statistics)
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
