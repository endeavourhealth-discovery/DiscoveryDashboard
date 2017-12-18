package org.endeavourhealth.adastrareceiver.api.endpoints;


import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.DashboardItemsEntity;
import org.endeavourhealth.adastrareceiver.api.database.models.LayoutItemsEntity;
import org.endeavourhealth.adastrareceiver.api.json.JsonDashboardItem;
import org.endeavourhealth.adastrareceiver.api.json.JsonLayoutItem;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/configuration")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the discovery dashboard configuration")
public class ConfigurationEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.getDashboardItems")
    @Path("/getDashboardItems")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getDashboardItems(@Context SecurityContext sc) throws Exception {

        return getDashboardItems();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.setDashboardItems")
    @Path("/setDashboardItems")
    @ApiOperation(value = "Set the batch size of the processor")
    public Response setDashboardItems(@Context SecurityContext sc,
                                       @ApiParam(value = "Json representation of the dashboard item") JsonDashboardItem item) throws Exception {

        Integer itemId = DashboardItemsEntity.saveDashboardItem(item);

        return Response
                .ok()
                .entity(itemId)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.getLayoutItems")
    @Path("/getLayoutItems")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response getLayoutItems(@Context SecurityContext sc,
                                   @ApiParam(value = "username") @QueryParam("username") String username) throws Exception {

        return getLayoutItems(username);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.setLayoutItems")
    @Path("/setLayoutItems")
    @ApiOperation(value = "Set the batch size of the processor")
    public Response setLayoutItems(@Context SecurityContext sc,
                                      @ApiParam(value = "Json representation of the dashboard item") JsonLayoutItem item) throws Exception {

        Integer itemId = LayoutItemsEntity.saveLayoutItem(item);

        return Response
                .ok()
                .entity(itemId)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.deleteLayoutItem")
    @Path("/deleteLayoutItem")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response deleteLayoutItem(@Context SecurityContext sc,
                                   @ApiParam(value = "id") @QueryParam("id") Integer id) throws Exception {

        return deleteLayoutItem(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.configuration.deleteDashboardItem")
    @Path("/deleteDashboardItem")
    @ApiOperation(value = "Gets the Application Information for the Discovery Dashboard")
    public Response deleteDashboardItem(@Context SecurityContext sc,
                                     @ApiParam(value = "id") @QueryParam("id") Integer id) throws Exception {

        return deleteDashboardItem(id);
    }

    private Response getDashboardItems() throws Exception {

        List<DashboardItemsEntity> items = DashboardItemsEntity.getDashboardItems();

        return Response
                .ok(items)
                .build();
    }

    private Response getLayoutItems(String username) throws Exception {

        List<LayoutItemsEntity> items = LayoutItemsEntity.getLayoutItems(username);

        return Response
                .ok(items)
                .build();
    }

    private Response deleteLayoutItem(Integer id) throws Exception {

        LayoutItemsEntity.deleteLayoutItem(id);

        return Response
                .ok()
                .entity("Layout item deleted")
                .build();
    }

    private Response deleteDashboardItem(Integer id) throws Exception {

        DashboardItemsEntity.deleteDashboardItem(id);

        return Response
                .ok()
                .entity("Dashboard item deleted")
                .build();
    }
}
