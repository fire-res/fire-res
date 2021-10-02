package io.github.fireres.unheated.surface.pipeline.firstgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.unheated.surface.generator.MaxAllowedMeanTemperatureGenerator;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Component
@Slf4j
@RequiredArgsConstructor
public class FirstGroupMaxAllowedMeanTemperatureEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    private final GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val meanBound = new MaxAllowedMeanTemperatureGenerator(time, t0)
                .generate();

        report.getFirstGroup().setMaxAllowedMeanTemperature(meanBound);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

}
