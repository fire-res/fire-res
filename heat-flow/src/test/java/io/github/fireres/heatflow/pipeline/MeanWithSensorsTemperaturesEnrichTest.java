package io.github.fireres.heatflow.pipeline;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.report.HeatFlowReport;
import io.github.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotEquals;

public class MeanWithSensorsTemperaturesEnrichTest extends AbstractTest {

    @Autowired
    private HeatFlowService heatFlowService;

    @Autowired
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Autowired
    private HeatFlowProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichSampleMeanWithSensors() {
        val report = heatFlowService.createReport(sample, reportProperties);

        val oldMeanTemperature = report.getMeanTemperature();
        val oldSensorsTemperatures = report.getSensorTemperatures();

        report.getProperties().setBound(0.5);
        reportEnrichPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);

        val newSampleMeanTemperature = report.getMeanTemperature();
        val newSampleSensorsTemperatures = report.getSensorTemperatures();

        assertNotEquals(oldMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSensorsTemperatures, newSampleSensorsTemperatures);
    }

}
