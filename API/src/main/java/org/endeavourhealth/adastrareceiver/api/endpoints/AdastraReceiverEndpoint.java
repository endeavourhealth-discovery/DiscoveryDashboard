package org.endeavourhealth.adastrareceiver.api.endpoints;
import adastraSchema.AdastraCaseDataExport;
import adastraWS.AdastraWebServiceSoap;
import adastraWS.AddUpdateCase;
import adastraWS.AddUpdateCaseResult;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
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
public class AdastraReceiverEndpoint implements AdastraWebServiceSoap {

    @Override
    public AddUpdateCaseResult addUpdateCase(String userName, String password, AddUpdateCase.Data data) {
        System.out.println("boom!!");
        return null;
    }

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
    @Timed(absolute = true, name="adastraReceiver.ConceptEndpoint.Get")
    @Path("/")
    @ApiOperation(value = "Returns a list of all concepts")
    public Response get(@Context SecurityContext sc,
                        @ApiParam(value = "Optional Concept Id") @QueryParam("conceptId") Integer conceptId,
                        @ApiParam(value = "Optional Name of concept") @QueryParam("conceptName") String conceptName,
                        @ApiParam(value = "Optional Array of concept Ids") @QueryParam("conceptIdList") List<Integer> conceptIdList
    ) throws Exception {
        System.out.println("Get Called");

        return Response
                .ok("hello")
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
