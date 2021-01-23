package io.github.therealmone.fireres.heatflow.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.heatflow.generator.HeatFlowBoundGenerator;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.heatflow.pipeline.sample.HeatFlowSampleEnrichType.SAMPLE_BOUND;
import static io.github.therealmone.fireres.heatflow.pipeline.sample.HeatFlowSampleEnrichType.SAMPLE_MEAN_WITH_SENSORS_TEMPERATURES;

public class SampleBoundEnricher implements SampleEnricher<HeatFlowReport, HeatFlowSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(HeatFlowReport report, HeatFlowSample sample) {
        val sampleProperties = generationProperties.getSampleById(sample.getId());
        val time = generationProperties.getGeneral().getTime();

        val bound = new HeatFlowBoundGenerator(time, sampleProperties.getHeatFlow().getBound())
                .generate();

        sample.setBound(bound);
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_BOUND.equals(enrichType);
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return List.of(SAMPLE_MEAN_WITH_SENSORS_TEMPERATURES);
    }

}
