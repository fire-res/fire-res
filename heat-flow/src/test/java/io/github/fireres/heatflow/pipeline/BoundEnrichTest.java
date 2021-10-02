package io.github.fireres.heatflow.pipeline;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.heatflow.report.HeatFlowReport;
import io.github.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotEquals;

public class BoundEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private HeatFlowService heatFlowService;

    @Autowired
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Test
    public void enrichSampleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val oldBound = report.getBound();

        report.getProperties().setBound(0.5);
        report.getProperties().getFunctionForm().getInterpolationPoints().clear();
        reportEnrichPipeline.accept(report, HeatFlowReportEnrichType.MAX_ALLOWED_FLOW);

        val newBound = report.getBound();

        assertNotEquals(oldBound, newBound);
    }

}
