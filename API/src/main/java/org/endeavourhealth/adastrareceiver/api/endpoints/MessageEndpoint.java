package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/message")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the messages stored")
public class MessageEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.message.getMessages")
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
    @Timed(absolute = true, name="adastraReceiver.message.getMessageCount")
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
}
