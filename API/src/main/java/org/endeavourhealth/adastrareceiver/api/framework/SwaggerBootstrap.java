package org.endeavourhealth.adastrareceiver.api.framework;

import com.codahale.metrics.jvm.*;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Info;
import io.swagger.models.Swagger;
import io.swagger.models.auth.OAuth2Definition;
import org.endeavourhealth.coreui.framework.config.ConfigService;
import org.endeavourhealth.adastrareceiver.api.metrics.AdastraReceiverInstrumentedFilterContextListener;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.management.ManagementFactory;

public class SwaggerBootstrap extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        Info info = new Info()
                .title("Adastra Receiver API")
                .description("Adastra Receiver API");

        System.out.println("API is running!!!");

        String baseAuthUrl = ConfigService.instance().getAuthConfig().getAuthServerUrl() +
                "/realms/" + ConfigService.instance().getAuthConfig().getRealm() + "/protocol/openid-connect";

        Swagger swagger = new Swagger().info(info);
        swagger.basePath("/api");
        swagger.securityDefinition("oauth",
                new OAuth2Definition()
                        .accessCode(baseAuthUrl + "/auth", baseAuthUrl + "/token")
                );
        new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);

        AdastraReceiverInstrumentedFilterContextListener.REGISTRY.register("Garbage Collection", new GarbageCollectorMetricSet());
        AdastraReceiverInstrumentedFilterContextListener.REGISTRY.register("Buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
        AdastraReceiverInstrumentedFilterContextListener.REGISTRY.register("Memory", new MemoryUsageGaugeSet());
        AdastraReceiverInstrumentedFilterContextListener.REGISTRY.register("Threads", new ThreadStatesGaugeSet());
        AdastraReceiverInstrumentedFilterContextListener.REGISTRY.register("File Descriptor", new FileDescriptorRatioGauge());
    }
}
