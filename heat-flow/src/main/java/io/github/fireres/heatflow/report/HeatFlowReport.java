package io.github.fireres.heatflow.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.heatflow.model.MaxAllowedFlow;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.model.MeanTemperature;
import io.github.fireres.heatflow.model.SensorTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class HeatFlowReport implements Report<HeatFlowProperties> {

    private final UUID id;
    private final Sample sample;

    private MaxAllowedFlow bound;
    private MeanTemperature meanTemperature;
    private List<SensorTemperature> sensorTemperatures;

    @Override
    public HeatFlowProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow();
    }
}