package org.endeavourhealth.adastrareceiver.api.endpoints;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.json.JsonGeneralSettings;
import org.endeavourhealth.adastrareceiver.api.json.JsonProcessorStatistics;
import org.endeavourhealth.adastrareceiver.api.managers.MessageSender;
import org.endeavourhealth.common.config.ConfigManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.soap.*;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.concurrent.*;

@Path("/processor")
@Metrics(registry = "adastraReceiverMetricRegistry")
@Api(description = "Initial api for all calls relating to the Adastra Receiver processor")
public class ProcessorEndpoint {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private static Long delay;
    private static Integer refresh;
    private static ScheduledFuture<?> future;
    private static JsonNode jsonConfig = null;

    public static void startProcessor() {
        getGeneralSettingsConfigValues();
        future = scheduler.scheduleAtFixedRate(new MessageSender(), 0L, delay, TimeUnit.SECONDS);
    }

    private static void getGeneralSettingsConfigValues() {
        try {
            jsonConfig = ConfigManager.getConfigurationAsJson("generalSettings");
            delay = jsonConfig.get("processorDelay").asLong();
            refresh = jsonConfig.get("refreshRate").asInt();
            MessageSender.setBatchSize(jsonConfig.get("processBatchSize").asInt());
            MessageSender.setUseKeycloak(jsonConfig.get("useKeycloak").asBoolean());
            MessageSender.setMessagingApiURL(jsonConfig.get("messagingApiUrl").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response getDatabaseSettingsConfigValues() {
        JsonNode dbConfig = null;
        try {
            dbConfig = ConfigManager.getConfigurationAsJson("adastra_receiverDB");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response
                .ok()
                .entity(dbConfig)
                .build();
    }

    private void saveConfig() throws Exception {

        JsonNode config = jsonConfig;
        ((ObjectNode)config).put("refreshRate", refresh);
        ((ObjectNode)config).put("processorDelay", delay);
        ((ObjectNode)config).put("messagingApiUrl", MessageSender.getMessagingApiURL());
        ((ObjectNode)config).put("useKeycloak", MessageSender.isUseKeycloak());
        ((ObjectNode)config).put("processBatchSize", MessageSender.getBatchSize());

        ConfigManager.setConfiguration("generalSettings", config.toString());

        jsonConfig = config;
    }



    private void saveDatabaseConfig(JsonNode dbConfig) throws Exception {

        ConfigManager.setConfiguration("adastra_receiverDB", dbConfig.toString());
    }

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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.getProcessorStatistics")
    @Path("/getProcessorStatistics")
    @ApiOperation(value = "Gets the processor statistics")
    public Response isRunning(@Context SecurityContext sc) throws Exception {

        return getProcessorStatistics();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.reloadConfig")
    @Path("/reloadConfig")
    @ApiOperation(value = "Clears the config cache after changing the config")
    public Response reloadConfig(@Context SecurityContext sc) throws Exception {

        jsonConfig = null;
        getGeneralSettingsConfigValues();

        return Response
                .ok()
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.getGeneralSettings")
    @Path("/getGeneralSettings")
    @ApiOperation(value = "Get the general settings of the application")
    public Response getGeneralSettings(@Context SecurityContext sc) throws Exception {

        return getGeneralSettings();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.setGeneralSettings")
    @Path("/setGeneralSettings")
    @ApiOperation(value = "Set the batch size of the processor")
    public Response setGeneralSettings(@Context SecurityContext sc,
                                 @ApiParam(value = "Json representation of the general settings") JsonGeneralSettings settings) throws Exception {

        refresh = settings.getRefreshRate();
        delay = settings.getProcessorDelay();
        MessageSender.setMessagingApiURL(settings.getMessagingApiUrl());
        MessageSender.setUseKeycloak(settings.isUseKeycloak());
        MessageSender.setBatchSize(settings.getProcessBatchSize());

        return Response
                .ok()
                .entity("Settings updated")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.saveConfig")
    @Path("/saveConfig")
    @ApiOperation(value = "Save the new config settings to the DB")
    public Response saveConfig(@Context SecurityContext sc) throws Exception {

        saveConfig();

        return Response
                .ok()
                .entity("Settings saved")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.getDatabaseConfig")
    @Path("/getDatabaseConfig")
    @ApiOperation(value = "Get the database settings of the application")
    public Response getDatabaseConfig(@Context SecurityContext sc) throws Exception {

        return getDatabaseSettingsConfigValues();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="adastraReceiver.processor.saveDatabaseConfig")
    @Path("/saveDatabaseConfig")
    @ApiOperation(value = "Save the database config settings to the DB")
    public Response saveDatabaseConfig(@Context SecurityContext sc,
                                       @ApiParam(value = "Json representation of the database settings") JsonNode dbConfig) throws Exception {

        saveDatabaseConfig(dbConfig);
        PersistenceManager.ClearEntityManager();
        resetAdastraWebServiceDBCache();

        return Response
                .ok()
                .entity("Database Settings saved")
                .build();
    }

    private Response getGeneralSettings() throws Exception {

        JsonGeneralSettings generalSettings = new JsonGeneralSettings();
        generalSettings.setRefreshRate(refresh);
        generalSettings.setProcessorDelay(delay);
        generalSettings.setMessagingApiUrl(MessageSender.getMessagingApiURL());
        generalSettings.setUseKeycloak(MessageSender.isUseKeycloak());
        generalSettings.setProcessBatchSize(MessageSender.getBatchSize());

        return Response
                .ok()
                .entity(generalSettings)
                .build();
    }

    private Response getProcessorStatistics() throws Exception {

        JsonProcessorStatistics statistics = new JsonProcessorStatistics();
        statistics.setRunning(!future.isDone());
        statistics.setLastRun(formatDate(MessageSender.getRunDate()));
        statistics.setNextRun(future.getDelay(TimeUnit.SECONDS));

        return Response
                .ok()
                .entity(statistics)
                .build();
    }

    private String formatDate(Instant lastRun) throws Exception {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM)
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );

        return formatter.format(lastRun);

    }

    private void resetAdastraWebServiceDBCache() throws Exception {
        String soapEndpointUrl = "http://localhost:8081/webservices/endeavour";
        String soapAction = "http://tempuri.org/webservices/adastra/AddUpdateCase";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "endeavour";
        String myNamespaceURI = "http://webservice/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:CelsiusToFahrenheit>
                        <myNamespace:Celsius>100</myNamespace:Celsius>
                    </myNamespace:CelsiusToFahrenheit>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("resetDBCache", myNamespace);
    }
}
