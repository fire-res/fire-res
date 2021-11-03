package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.unheated.surface.generator.MaxAllowedMeanTemperatureGenerator;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MAX_ALLOWED_MEAN_TEMPERATURE;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Component
@Slf4j
@RequiredArgsConstructor
public class UnheatedSurfaceMaxAllowedMeanTemperatureEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generalProperties.getTime();
        val t0 = generalProperties.getEnvironmentTemperature();
        val bound = report.getProperties().getBound();
        val type = report.getProperties().getType();

        val meanBound = new MaxAllowedMeanTemperatureGenerator(time, t0, bound, type)
                .generate();

        report.setMaxAllowedMeanTemperature(meanBound);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAX_ALLOWED_MEAN_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(
                MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE,
                MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

}
