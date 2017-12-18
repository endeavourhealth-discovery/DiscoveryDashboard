package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.enums.HealthStatus;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.adastrareceiver.api.json.JsonApplicationInformation;
import org.endeavourhealth.adastrareceiver.api.json.JsonDashboardInformation;
import org.endeavourhealth.adastrareceiver.api.json.JsonDashboardStatistics;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/dashboard")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver dashboard")
public class DashboardEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getApplicationInformation")
    @Path("/getApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getApplicationInformation();
    }

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getEMISApplicationInformation")
    @Path("/getEMISApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getEMISApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getEMISApplicationInformation();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getVisionApplicationInformation")
    @Path("/getVisionApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getVisionApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getVisionApplicationInformation();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getRabbitApplicationInformation")
    @Path("/getRabbitApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getRabbitApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getRabbitApplicationInformation();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getBartsApplicationInformation")
    @Path("/getBartsApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getBartsApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getBartsApplicationInformation();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.getHomertonApplicationInformation")
    @Path("/getHomertonApplicationInformation")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getHomertonApplicationInformation(@Context SecurityContext sc) throws Exception {

        return getHomertonApplicationInformation();
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

    private JsonApplicationInformation getCountInformation(String title, byte messageStatus, String status) throws Exception {
        JsonApplicationInformation information = new JsonApplicationInformation();
        information.setLabelText(title);
        information.setCount(MessageStoreEntity.getTotalNumberOfMessages(messageStatus));
        information.setStatus(status);

        return information;
    }

    private JsonApplicationInformation getGenericCountInformation(String title, Long value, String status) throws Exception {
        JsonApplicationInformation information = new JsonApplicationInformation();
        information.setLabelText(title);
        information.setCount(value);
        information.setStatus(status);

        return information;
    }

    private JsonApplicationInformation getGenericTextInformation(String title, String value) throws Exception {
        JsonApplicationInformation information = new JsonApplicationInformation();
        information.setLabelText(title);
        information.setValueText(value);

        return information;
    }

    private Response getApplicationInformation() throws Exception {

        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.SUCCESS.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getCountInformation("Total Messages Received", MessageStatus.ALL.getMessageStatus(), HealthStatus.PRIMARY.getHealthStatus() ));
        informationList.add(getCountInformation("Received but not sent", MessageStatus.RECEIVED.getMessageStatus(), HealthStatus.WARNING.getHealthStatus() ));
        informationList.add(getCountInformation("Sent to API", MessageStatus.PROCESSED.getMessageStatus(), HealthStatus.SUCCESS.getHealthStatus() ));
        informationList.add(getCountInformation("Errors", MessageStatus.ERROR.getMessageStatus(), HealthStatus.DANGER.getHealthStatus() ));

        JsonApplicationInformation processor = new JsonApplicationInformation();
        processor.setLabelText("Processor Running");
        processor.setValueText("True");

        informationList.add(processor);

        dashboardInformation.setApplicationInformation(informationList);


        return Response
                .ok(dashboardInformation)
                .build();
    }

    private Response getEMISApplicationInformation() throws Exception {
        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.WARNING.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getGenericCountInformation("Files in last batch", 53L, HealthStatus.PRIMARY.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Number of practices", 5L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Total files in last month", 3654L, HealthStatus.SUCCESS.getHealthStatus()));

        dashboardInformation.setApplicationInformation(informationList);
        return Response
                .ok(dashboardInformation)
                .build();
    }

    private Response getVisionApplicationInformation() throws Exception {
        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.DANGER.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getGenericCountInformation("Files in last batch", 1L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Number of practices", 2L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Total files in last month", 3654L, HealthStatus.SUCCESS.getHealthStatus() ));
        informationList.add(getGenericTextInformation("Poller running", "True"));

        dashboardInformation.setApplicationInformation(informationList);

        return Response
                .ok(dashboardInformation)
                .build();
    }

    private Response getRabbitApplicationInformation() throws Exception {
        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.WARNING.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getGenericCountInformation("Queues under threshold", 22L, HealthStatus.SUCCESS.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Queues over threshold", 2L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericTextInformation("Largest queue", "Barts queue"));

        dashboardInformation.setApplicationInformation(informationList);

        return Response
                .ok(dashboardInformation)
                .build();
    }

    private Response getBartsApplicationInformation() throws Exception {
        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.DANGER.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getGenericCountInformation("Total Messages", 22545L, HealthStatus.PRIMARY.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Messages in DLQ", 52L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericTextInformation("Last message received", "12/12/2017 14:55:32"));

        dashboardInformation.setApplicationInformation(informationList);

        return Response
                .ok(dashboardInformation)
                .build();
    }

    private Response getHomertonApplicationInformation() throws Exception {
        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.DANGER.getHealthStatus());

        List<JsonApplicationInformation> informationList = new ArrayList<>();

        informationList.add(getGenericCountInformation("Total Messages", 1587L, HealthStatus.SUCCESS.getHealthStatus() ));
        informationList.add(getGenericCountInformation("Messages in DLQ", 1L, HealthStatus.DANGER.getHealthStatus() ));
        informationList.add(getGenericTextInformation("Last message received", "13/12/2017 12:55:32"));

        dashboardInformation.setApplicationInformation(informationList);

        return Response
                .ok(dashboardInformation)
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
