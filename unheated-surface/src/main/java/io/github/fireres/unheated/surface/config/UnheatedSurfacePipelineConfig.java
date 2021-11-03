package io.github.fireres.unheated.surface.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceMaxAllowedMeanTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceMaxAllowedThermocoupleTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceMeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UnheatedSurfacePipelineConfig {

    @Bean
    public ReportEnrichPipeline<UnheatedSurfaceReport> unheatedSurfacePipeline(
            UnheatedSurfaceMaxAllowedMeanTemperatureEnricher unheatedSurfaceMaxAllowedMeanTemperatureEnricher,
            UnheatedSurfaceMaxAllowedThermocoupleTemperatureEnricher unheatedSurfaceMaxAllowedThermocoupleTemperatureEnricher,
            UnheatedSurfaceMeanWithThermocoupleTemperaturesEnricher unheatedSurfaceMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                unheatedSurfaceMaxAllowedMeanTemperatureEnricher,
                unheatedSurfaceMaxAllowedThermocoupleTemperatureEnricher,
                unheatedSurfaceMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
