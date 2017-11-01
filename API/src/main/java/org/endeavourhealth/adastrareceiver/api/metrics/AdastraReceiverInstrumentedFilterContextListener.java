package org.endeavourhealth.adastrareceiver.api.metrics;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

public class AdastraReceiverInstrumentedFilterContextListener extends InstrumentedFilterContextListener {

    public static final MetricRegistry REGISTRY = SharedMetricRegistries.getOrCreate("adastraReceiverMetricRegistry");

    @Override
    protected MetricRegistry getMetricRegistry() {
        return REGISTRY;
    }
}
