package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;

@Slf4j
@Component
public class ExcessPressureBasePressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Override
    public void enrich(ExcessPressureReport report) {
        report.setBasePressure(report.getProperties().getBasePressure());
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return BASE_PRESSURE.equals(enrichType);
    }
}
