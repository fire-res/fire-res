package io.github.fireres.excess.pressure.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.excess.pressure.pipeline.ExcessPressureBasePressureEnricher;
import io.github.fireres.excess.pressure.pipeline.ExcessPressureMaxAllowedPressureEnricher;
import io.github.fireres.excess.pressure.pipeline.ExcessPressureMinAllowedPressureEnricher;
import io.github.fireres.excess.pressure.pipeline.ExcessPressureEnricher;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExcessPressurePipelineConfig {

    @Bean
    public ReportEnrichPipeline<ExcessPressureReport> excessPressurePipeline(
            ExcessPressureBasePressureEnricher excessPressureBasePressureEnricher,
            ExcessPressureMinAllowedPressureEnricher excessPressureMinAllowedPressureEnricher,
            ExcessPressureMaxAllowedPressureEnricher excessPressureMaxAllowedPressureEnricher,
            ExcessPressureEnricher excessPressureEnricher)
    {
        return new DefaultReportEnrichPipeline<>(List.of(
                excessPressureBasePressureEnricher,
                excessPressureMinAllowedPressureEnricher,
                excessPressureMaxAllowedPressureEnricher,
                excessPressureEnricher
        ));
    }

}
