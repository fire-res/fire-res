package io.github.fireres.heatflow.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.ReportType;
import io.github.fireres.core.model.Sample;
import io.github.fireres.heatflow.model.MaxAllowedFlow;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.model.MeanTemperature;
import io.github.fireres.heatflow.model.SensorTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static io.github.fireres.heatflow.report.HeatFlowReportType.HEAT_FLOW;

@Data
@RequiredArgsConstructor
public class HeatFlowReport implements Report<HeatFlowProperties> {

    private final ReportType type = HEAT_FLOW;

    private final HeatFlowProperties properties;
    private final Sample sample;

    private MaxAllowedFlow bound;
    private MeanTemperature meanTemperature;
    private List<SensorTemperature> sensorTemperatures;

    @Override
    public UUID getId() {
        return properties.getId();
    }
}