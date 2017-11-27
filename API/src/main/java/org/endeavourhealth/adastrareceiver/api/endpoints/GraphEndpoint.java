package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/graph")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the graphs")
public class GraphEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.message.getGraphValues")
    @Path("/getGraphValues")
    @ApiOperation(value = "Gets messages paginated on the server side")
    public Response getGraphValues(@Context SecurityContext sc) throws Exception {
        System.out.println("getting graph details");

        List results = MessageStoreEntity.getGraphValues();

        return Response
                .ok()
                .entity(results)
                .build();
    }
}
