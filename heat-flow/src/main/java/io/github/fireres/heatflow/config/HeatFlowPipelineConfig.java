package io.github.fireres.heatflow.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.heatflow.pipeline.HeatFlowBoundEnricher;
import io.github.fireres.heatflow.pipeline.HeatFlowMeanWithSensorsTemperaturesEnricher;
import io.github.fireres.heatflow.report.HeatFlowReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HeatFlowPipelineConfig {

    @Bean
    public ReportEnrichPipeline<HeatFlowReport> heatFlowPipeline(
            HeatFlowBoundEnricher heatFlowBoundEnricher,
            HeatFlowMeanWithSensorsTemperaturesEnricher heatFlowMeanWithSensorsTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                heatFlowBoundEnricher,
                heatFlowMeanWithSensorsTemperaturesEnricher
        ));
    }

}
