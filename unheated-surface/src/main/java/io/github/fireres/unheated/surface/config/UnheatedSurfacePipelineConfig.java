package io.github.fireres.unheated.surface.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMaxAllowedMeanTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMaxAllowedThermocoupleTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.firstgroup.FirstGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.pipeline.secondgroup.SecondGroupMaxAllowedTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.secondgroup.SecondGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.pipeline.thirdgroup.ThirdGroupMaxAllowedTemperatureEnricher;
import io.github.fireres.unheated.surface.pipeline.thirdgroup.ThirdGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UnheatedSurfacePipelineConfig {

    @Bean
    public ReportEnrichPipeline<UnheatedSurfaceReport> unheatedSurfacePipeline(
            FirstGroupMaxAllowedMeanTemperatureEnricher firstGroupMaxAllowedMeanTemperatureEnricher,
            FirstGroupMaxAllowedThermocoupleTemperatureEnricher firstGroupMaxAllowedThermocoupleTemperatureEnricher,
            FirstGroupMeanWithThermocoupleTemperaturesEnricher firstGroupMeanWithThermocoupleTemperaturesEnricher,

            SecondGroupMaxAllowedTemperatureEnricher secondGroupMaxAllowedTemperatureEnricher,
            SecondGroupMeanWithThermocoupleTemperaturesEnricher secondGroupMeanWithThermocoupleTemperaturesEnricher,

            ThirdGroupMaxAllowedTemperatureEnricher thirdGroupMaxAllowedTemperatureEnricher,
            ThirdGroupMeanWithThermocoupleTemperaturesEnricher thirdGroupMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                firstGroupMaxAllowedMeanTemperatureEnricher,
                firstGroupMaxAllowedThermocoupleTemperatureEnricher,
                firstGroupMeanWithThermocoupleTemperaturesEnricher,
                secondGroupMaxAllowedTemperatureEnricher,
                secondGroupMeanWithThermocoupleTemperaturesEnricher,
                thirdGroupMaxAllowedTemperatureEnricher,
                thirdGroupMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
