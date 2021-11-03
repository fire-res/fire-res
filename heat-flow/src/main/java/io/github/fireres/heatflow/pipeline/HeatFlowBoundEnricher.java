package io.github.fireres.heatflow.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.heatflow.generator.MaxAllowedFlowGenerator;
import io.github.fireres.heatflow.report.HeatFlowReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeatFlowBoundEnricher implements ReportEnricher<HeatFlowReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(HeatFlowReport report) {
        val time = generalProperties.getTime();

        val bound = new MaxAllowedFlowGenerator(time, report.getProperties().getBound())
                .generate();

        report.setBound(bound);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return HeatFlowReportEnrichType.MAX_ALLOWED_FLOW.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

}
