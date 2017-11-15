package org.endeavourhealth.adastrareceiver.api.endpoints;
import adastraSchema.AdastraCaseDataExport;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.managers.MessageProcessor;
import org.endeavourhealth.common.utility.XmlHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Path("/adastra")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver")
public class AdastraReceiverEndpoint  {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Timed(absolute = true, name="adastraReceiver.ConceptEndpoint.Post")
    @Path("/postMessage")
    @ApiOperation(value = "Posts Message")
    public Response postMessage(@Context SecurityContext sc,
                         @ApiParam(value = "XML message") String message
    ) throws Exception {
        System.out.println("posting message");

        return saveMessage(message);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.messageCount")
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
    @Timed(absolute = true, name="adastraReceiver.adastra.startProcessor")
    @Path("/startProcessor")
    @ApiOperation(value = "Starts the message Processor")
    public Response startProcessor(@Context SecurityContext sc) throws Exception {
        System.out.println("Starting Processor");

        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.startProcessor();

        return Response
                .ok()
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.stopProcessor")
    @Path("/stopProcessor")
    @ApiOperation(value = "Stops the message Processor")
    public Response stopProcessor(@Context SecurityContext sc) throws Exception {
        System.out.println("Stopping Processor");

        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.stopProcessor();

        return Response
                .ok()
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.isRunning")
    @Path("/isRunning")
    @ApiOperation(value = "Checks if the processor is running")
    public Response isRunning(@Context SecurityContext sc) throws Exception {
        System.out.println("Checking Status");

        MessageProcessor messageProcessor = new MessageProcessor();
        boolean isRunning = messageProcessor.processorStatus();

        return Response
                .ok(isRunning)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.unsentMessages")
    @Path("/unsentMessages")
    @ApiOperation(value = "Gets the 10 earliest unsent messages")
    public Response message(@Context SecurityContext sc) throws Exception {
        System.out.println("getting messages");

        List<MessageStoreEntity> messages = MessageStoreEntity.getEarliestUnsentMessages();
        System.out.println(messages.size());

        return Response
                .ok()
                .entity(messages)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.getMessages")
    @Path("/getMessages")
    @ApiOperation(value = "Gets messages paginated on the server side")
    public Response getMessages(@Context SecurityContext sc,
                                @ApiParam(value = "Optional page number (defaults to 1 if not provided)") @QueryParam("pageNumber") Integer pageNumber,
                                @ApiParam(value = "Optional page size (defaults to 20 if not provided)")@QueryParam("pageSize") Integer pageSize,
                                @ApiParam(value = "Optional order column (defaults to name if not provided)")@QueryParam("orderColumn") String orderColumn,
                                @ApiParam(value = "Optional ordering direction (defaults to descending if not provided)")@QueryParam("ascending") boolean ascending,
                                @ApiParam(value = "Optional array of message status' to return")@QueryParam("statusList") List<Integer> statusList) throws Exception {
        System.out.println("getting messages");

        if (pageNumber == null)
            pageNumber = 1;
        if (pageSize == null)
            pageSize = 10;
        if (orderColumn == null)
            orderColumn = "id";

        List<MessageStoreEntity> messages = MessageStoreEntity.getMessages(pageNumber, pageSize, orderColumn, ascending, statusList);
        System.out.println(messages.size());

        return Response
                .ok()
                .entity(messages)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.getMessageCount")
    @Path("/getMessageCount")
    @ApiOperation(value = "Gets messages paginated on the server side")
    public Response getMessageCount(@Context SecurityContext sc,
                                @ApiParam(value = "Optional array of message status' to return")@QueryParam("statusList") List<Integer> statusList) throws Exception {

        Long messages = MessageStoreEntity.getMessageCount(statusList);

        return Response
                .ok()
                .entity(messages)
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.adastra.clearConfigCache")
    @Path("/clearConfigCache")
    @ApiOperation(value = "Clears the config cache after changing the config")
    public Response clearConfigCache(@Context SecurityContext sc) throws Exception {
        System.out.println("clearing Cache");

        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.clearConfigCache();

        return Response
                .ok()
                .build();
    }

    private Response getMessageCount(byte status) throws Exception {

        Long messageCount = MessageStoreEntity.getTotalNumberOfMessages(status);
        return Response
                .ok(messageCount)
                .build();
    }

    private AdastraCaseDataExport deserialisePayload(String message) throws Exception {

        return XmlHelper.deserialize(message, AdastraCaseDataExport.class);
    }

    private String getSendingOrganisation(AdastraCaseDataExport caseData) throws Exception {

        AdastraCaseDataExport.Patient patient = caseData.getPatient();

        return patient.getGpRegistration().getSurgeryNationalCode();
    }

    private Response saveMessage(String message) throws Exception {

        AdastraCaseDataExport caseData = deserialisePayload(message);
        AdastraCaseDataExport.Patient patient = caseData.getPatient();

        String source = getSendingOrganisation(caseData);
        System.out.println(source);

        MessageStoreEntity ms = new MessageStoreEntity();
        ms.setMessagePayload(message);
        ms.setReceivedDateTime(Timestamp.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
        ms.setSource(1);
        ms.setStatus((byte)0);

        MessageStoreEntity.storeMessage(ms);

        return Response
                .ok(message)
                .build();
    }

}
