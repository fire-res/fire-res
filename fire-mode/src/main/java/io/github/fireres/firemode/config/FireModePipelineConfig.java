package io.github.fireres.firemode.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.firemode.pipeline.FireModeFurnaceTemperatureEnricher;
import io.github.fireres.firemode.pipeline.FireModeMaintainedTemperaturesEnricher;
import io.github.fireres.firemode.pipeline.FireModeMaxAllowedTemperatureEnricher;
import io.github.fireres.firemode.pipeline.FireModeMeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.firemode.pipeline.FireModeMinAllowedTemperatureEnricher;
import io.github.fireres.firemode.pipeline.FireModeStandardTemperatureEnricher;
import io.github.fireres.firemode.report.FireModeReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FireModePipelineConfig {

    @Bean
    public ReportEnrichPipeline<FireModeReport> fireModePipeline(
            FireModeStandardTemperatureEnricher fireModeStandardTemperatureEnricher,
            FireModeMinAllowedTemperatureEnricher fireModeMinAllowedTemperatureEnricher,
            FireModeMaxAllowedTemperatureEnricher fireModeMaxAllowedTemperatureEnricher,
            FireModeFurnaceTemperatureEnricher fireModeFurnaceTemperatureEnricher,
            FireModeMeanWithThermocoupleTemperaturesEnricher fireModeMeanWithThermocoupleTemperaturesEnricher,
            FireModeMaintainedTemperaturesEnricher fireModeMaintainedTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                fireModeStandardTemperatureEnricher,
                fireModeMinAllowedTemperatureEnricher,
                fireModeMaxAllowedTemperatureEnricher,
                fireModeFurnaceTemperatureEnricher,
                fireModeMeanWithThermocoupleTemperaturesEnricher,
                fireModeMaintainedTemperaturesEnricher
        ));
    }

}
