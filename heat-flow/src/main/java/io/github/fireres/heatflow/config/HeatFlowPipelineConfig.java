package io.github.fireres.heatflow.config;

import io.github.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.heatflow.pipeline.BoundEnricher;
import io.github.fireres.heatflow.pipeline.MeanWithSensorsTemperaturesEnricher;
import io.github.fireres.heatflow.report.HeatFlowReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HeatFlowPipelineConfig {

    @Bean
    public ReportEnrichPipeline<HeatFlowReport> heatFlowPipeline(
            BoundEnricher boundEnricher,
            MeanWithSensorsTemperaturesEnricher meanWithSensorsTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                boundEnricher,
                meanWithSensorsTemperaturesEnricher
        ));
    }

}
