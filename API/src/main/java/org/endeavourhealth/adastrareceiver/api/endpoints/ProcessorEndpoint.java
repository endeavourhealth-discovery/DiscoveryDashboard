package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.managers.MessageProcessor;
import org.endeavourhealth.adastrareceiver.api.managers.MessageSender;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Path("/processor")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver processor")
public class ProcessorEndpoint {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private static Future<?> future = scheduler.scheduleAtFixedRate(new MessageSender(), 0L, 10L, TimeUnit.SECONDS);;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.startProcessor")
    @Path("/startProcessor")
    @ApiOperation(value = "Starts the message Processor")
    public Response startProcessor(@Context SecurityContext sc) throws Exception {

        if (future.isDone()) {
            future = scheduler.scheduleAtFixedRate(new MessageSender(), 0L, 10L, TimeUnit.SECONDS);
        }

        return Response
                .ok()
                .entity("Processor Started")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.stopProcessor")
    @Path("/stopProcessor")
    @ApiOperation(value = "Stops the message Processor")
    public Response stopProcessor(@Context SecurityContext sc) throws Exception {
        System.out.println("Stopping Processor");

        if (!future.isDone())
            future.cancel(false);

        return Response
                .ok("Processor Stopped")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.isRunning")
    @Path("/isRunning")
    @ApiOperation(value = "Checks if the processor is running")
    public Response isRunning(@Context SecurityContext sc) throws Exception {
        boolean isRunning = !future.isDone();

        return Response
                .ok(isRunning)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.unsentMessages")
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
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.dashboard.clearConfigCache")
    @Path("/clearConfigCache")
    @ApiOperation(value = "Clears the config cache after changing the config")
    public Response clearConfigCache(@Context SecurityContext sc) throws Exception {
        System.out.println("clearing Cache");

        return Response
                .ok()
                .build();
    }

}
