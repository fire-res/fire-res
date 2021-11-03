package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.fireres.excess.pressure.generator.MaxAllowedPressureGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MAX_ALLOWED_PRESSURE;
import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExcessPressureMaxAllowedPressureEnricher implements ReportEnricher<ExcessPressureReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generalProperties.getTime();
        val delta = report.getProperties().getDelta();

        val maxAllowedPressure = new MaxAllowedPressureGenerator(time, delta)
                .generate();

        report.setMaxAllowedPressure(maxAllowedPressure);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAX_ALLOWED_PRESSURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(PRESSURE);
    }
}
