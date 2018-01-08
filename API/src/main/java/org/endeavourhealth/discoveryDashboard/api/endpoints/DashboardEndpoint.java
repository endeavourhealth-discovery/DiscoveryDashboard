package org.endeavourhealth.discoveryDashboard.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.endeavourhealth.dashboardinformation.enums.HealthStatus;
import org.endeavourhealth.dashboardinformation.json.JsonApplicationInformation;
import org.endeavourhealth.dashboardinformation.json.JsonDashboardInformation;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/dashboard")
@Metrics(registry = "discoveryDashboardMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver dashboard")
public class DashboardEndpoint {

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

}
