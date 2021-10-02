package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
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
public class MaxAllowedPressureEnricher implements ReportEnricher<ExcessPressureReport> {

    private final GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generationProperties.getGeneral().getTime();
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