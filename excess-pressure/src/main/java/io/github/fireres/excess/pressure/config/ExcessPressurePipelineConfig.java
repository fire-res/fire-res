package io.github.fireres.excess.pressure.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.excess.pressure.pipeline.BasePressureEnricher;
import io.github.fireres.excess.pressure.pipeline.MaxAllowedPressureEnricher;
import io.github.fireres.excess.pressure.pipeline.MinAllowedPressureEnricher;
import io.github.fireres.excess.pressure.pipeline.PressureEnricher;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExcessPressurePipelineConfig {

    @Bean
    public ReportEnrichPipeline<ExcessPressureReport> excessPressurePipeline(
            BasePressureEnricher basePressureEnricher,
            MinAllowedPressureEnricher minAllowedPressureEnricher,
            MaxAllowedPressureEnricher maxAllowedPressureEnricher,
            PressureEnricher pressureEnricher)
    {
        return new DefaultReportEnrichPipeline<>(List.of(
                basePressureEnricher,
                minAllowedPressureEnricher,
                maxAllowedPressureEnricher,
                pressureEnricher
        ));
    }

}
