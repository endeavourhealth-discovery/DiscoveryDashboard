package org.endeavourhealth.adastrareceiver.api.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;


public class AdastraReceiverMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry adastraReceiverMetricRegistry = AdastraReceiverInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return adastraReceiverMetricRegistry;
    }
}
