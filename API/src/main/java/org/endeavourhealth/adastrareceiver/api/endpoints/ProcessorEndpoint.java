package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.models.MessageStoreEntity;
import org.endeavourhealth.adastrareceiver.api.managers.MessageProcessor;
import org.endeavourhealth.adastrareceiver.api.managers.MessageSender;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

import static org.endeavourhealth.adastrareceiver.api.managers.MessageSender.getRunDate;

@Path("/processor")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver processor")
public class ProcessorEndpoint {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private static Long delay = 10L;
    private static ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(new MessageSender(), 0L, delay, TimeUnit.SECONDS);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.startProcessor")
    @Path("/startProcessor")
    @ApiOperation(value = "Starts the message Processor")
    public Response startProcessor(@Context SecurityContext sc) throws Exception {

        if (future.isDone()) {
            future = scheduler.scheduleAtFixedRate(new MessageSender(), 0L, delay, TimeUnit.SECONDS);
        }

        return Response
                .ok()
                .entity("Processor Started")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.stopProcessor")
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
    @Timed(absolute = true, name="adastraReceiver.processor.isRunning")
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
    @Timed(absolute = true, name="adastraReceiver.processor.unsentMessages")
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
    @Timed(absolute = true, name="adastraReceiver.processor.clearConfigCache")
    @Path("/clearConfigCache")
    @ApiOperation(value = "Clears the config cache after changing the config")
    public Response clearConfigCache(@Context SecurityContext sc) throws Exception {
        System.out.println("clearing Cache");

        return Response
                .ok()
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.nextRun")
    @Path("/nextRun")
    @ApiOperation(value = "Gets the next run time of the processor")
    public Response nextRun(@Context SecurityContext sc) throws Exception {

        Long secondsUntilRun = future.getDelay(TimeUnit.SECONDS);
        return Response
                .ok()
                .entity(secondsUntilRun)
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.setDelay")
    @Path("/setDelay")
    @ApiOperation(value = "Set the delay of the processor")
    public Response setDelay(@Context SecurityContext sc,
                             @ApiParam(value = "delay value") @QueryParam("delayValue") Long delayValue) throws Exception {

        delay = delayValue;
        return Response
                .ok()
                .entity("Delay set to " + delayValue + " seconds")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.getDelay")
    @Path("/getDelay")
    @ApiOperation(value = "Get the delay of the processor")
    public Response setDelay(@Context SecurityContext sc) throws Exception {

        return Response
                .ok()
                .entity(delay)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.getLastRun")
    @Path("/getLastRun")
    @ApiOperation(value = "Get the last run time of the processor")
    public Response getLastRun(@Context SecurityContext sc) throws Exception {

        Instant lastSent = MessageSender.getRunDate();

        return formatDate(lastSent);
    }

    private Response formatDate(Instant lastRun) throws Exception {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM)
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );

        String output = formatter.format(lastRun);

        return Response
                .ok()
                .entity(output)
                .build();
    }
}
