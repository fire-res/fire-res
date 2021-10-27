package io.github.fireres.unheated.surface.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.unheated.surface.pipeline.MaxAllowedMeanTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.MaxAllowedThermocoupleTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.MeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UnheatedSurfacePipelineConfig {

    @Bean
    public ReportEnrichPipeline<UnheatedSurfaceReport> unheatedSurfacePipeline(
            MaxAllowedMeanTemperatureEnricher maxAllowedMeanTemperatureEnricher,
            MaxAllowedThermocoupleTemperatureEnricher maxAllowedThermocoupleTemperatureEnricher,
            MeanWithThermocoupleTemperaturesEnricher meanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                maxAllowedMeanTemperatureEnricher,
                maxAllowedThermocoupleTemperatureEnricher,
                meanWithThermocoupleTemperaturesEnricher
        ));
    }

}
