package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

public class PrimaryGroupTemperaturesEnrichTest extends AbstractTest {

    @Autowired
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private UnheatedSurfaceProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichThermocoupleBound() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val oldMeanTemperature = report.getMeanTemperature();
        val oldThermocoupleTemperatures = report.getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newMeanTemperature = report.getMeanTemperature();
        val newThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldMeanTemperature, newMeanTemperature);
        assertNotEquals(oldThermocoupleTemperatures, newThermocoupleTemperatures);
    }

}
