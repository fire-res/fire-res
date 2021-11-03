package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;
import static org.junit.Assert.assertNotEquals;

public class SampleBasePressureEnrichTest extends AbstractTest {

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Autowired
    private ExcessPressureProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichBasePressure() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val oldBasePressure = report.getBasePressure();

        report.getProperties().setBasePressure(10000.0);
        reportEnrichPipeline.accept(report, BASE_PRESSURE);

        val newBasePressure = report.getBasePressure();

        assertNotEquals(oldBasePressure, newBasePressure);
    }

}
