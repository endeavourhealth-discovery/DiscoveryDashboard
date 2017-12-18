package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.adastrareceiver.api.json.JsonGraphOptions;
import org.endeavourhealth.dashboardinformation.enums.HealthStatus;
import org.endeavourhealth.dashboardinformation.json.JsonDashboardInformation;
import org.endeavourhealth.dashboardinformation.json.JsonGraphResults;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/graph")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the graphs")
public class GraphEndpoint {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.message.getGraphValues")
    @Path("/getGraphValues")
    @ApiOperation(value = "Gets messages paginated on the server side")
    public Response getGraphValues(@Context SecurityContext sc, JsonGraphOptions options) throws Exception {
        System.out.println("getting graph details");

        return getGraphResults(options);

    }

    private Response getGraphResults(JsonGraphOptions options) throws Exception {

        JsonDashboardInformation dashboardInformation = new JsonDashboardInformation();
        dashboardInformation.setAppHealth(HealthStatus.SUCCESS.getHealthStatus());

        List<JsonGraphResults> results = new ArrayList<>();

        String tableGUID = UUID.randomUUID().toString().replace("-", "");

        MessageStoreEntity.initialiseReportResultTable(options, tableGUID);

        results.add(createGraphResults("Received", MessageStatus.RECEIVED.getMessageStatus(), options.getPeriod(), tableGUID));
        results.add(createGraphResults("Sent", MessageStatus.PROCESSED.getMessageStatus(), options.getPeriod(), tableGUID));
        results.add(createGraphResults("Errors", MessageStatus.ERROR.getMessageStatus(), options.getPeriod(), tableGUID));

        dashboardInformation.setGraphResults(results);
        MessageStoreEntity.deleteDateRangeTable(tableGUID);
        return Response
                .ok()
                .entity(dashboardInformation)
                .build();
    }

    private JsonGraphResults createGraphResults(String title, byte status, String period, String tableGUID) throws Exception {
        JsonGraphResults graph = new JsonGraphResults();
        graph.setTitle(title);
        graph.setResults(MessageStoreEntity.getGraphValues(status, period, tableGUID));

        return graph;
    }
}
