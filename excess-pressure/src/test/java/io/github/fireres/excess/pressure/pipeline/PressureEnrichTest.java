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

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;
import static org.junit.Assert.assertNotEquals;

public class PressureEnrichTest extends AbstractTest {

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Autowired
    private ExcessPressureProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichSamplePressure() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val oldPressure = report.getPressure();

        reportEnrichPipeline.accept(report, PRESSURE);
        val newPressure = report.getPressure();

        assertNotEquals(oldPressure, newPressure);
    }

}
