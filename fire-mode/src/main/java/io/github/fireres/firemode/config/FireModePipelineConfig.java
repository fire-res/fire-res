package io.github.fireres.firemode.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.firemode.pipeline.FurnaceTemperatureEnricher;
import io.github.fireres.firemode.pipeline.MaintainedTemperaturesEnricher;
import io.github.fireres.firemode.pipeline.MaxAllowedTemperatureEnricher;
import io.github.fireres.firemode.pipeline.MeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.firemode.pipeline.MinAllowedTemperatureEnricher;
import io.github.fireres.firemode.pipeline.StandardTemperatureEnricher;
import io.github.fireres.firemode.report.FireModeReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FireModePipelineConfig {

    @Bean
    public ReportEnrichPipeline<FireModeReport> fireModePipeline(
            StandardTemperatureEnricher standardTemperatureEnricher,
            MinAllowedTemperatureEnricher minAllowedTemperatureEnricher,
            MaxAllowedTemperatureEnricher maxAllowedTemperatureEnricher,
            FurnaceTemperatureEnricher furnaceTemperatureEnricher,
            MeanWithThermocoupleTemperaturesEnricher meanWithThermocoupleTemperaturesEnricher,
            MaintainedTemperaturesEnricher maintainedTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                standardTemperatureEnricher,
                minAllowedTemperatureEnricher,
                maxAllowedTemperatureEnricher,
                furnaceTemperatureEnricher,
                meanWithThermocoupleTemperaturesEnricher,
                maintainedTemperaturesEnricher
        ));
    }

}
