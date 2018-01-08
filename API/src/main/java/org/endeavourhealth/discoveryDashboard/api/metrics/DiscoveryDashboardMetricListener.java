package org.endeavourhealth.discoveryDashboard.api.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;


public class DiscoveryDashboardMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry discoveryDashboardMetricRegistry = DiscoveryDashboardInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return discoveryDashboardMetricRegistry;
    }
}
