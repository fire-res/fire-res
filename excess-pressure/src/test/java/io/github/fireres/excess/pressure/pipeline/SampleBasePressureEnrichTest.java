package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.config.TestConfig;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;
import static org.junit.Assert.assertNotEquals;

public class SampleBasePressureEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichBasePressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val oldBasePressure = report.getBasePressure();

        report.getProperties().setBasePressure(10000.0);
        reportEnrichPipeline.accept(report, BASE_PRESSURE);

        val newBasePressure = report.getBasePressure();

        assertNotEquals(oldBasePressure, newBasePressure);
    }

}
