package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MAX_ALLOWED_MEAN_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

public class PrimaryGroupMeanBoundEnrichTest extends AbstractTest {

    @Autowired
    private GeneralProperties generalProperties;

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Autowired
    private UnheatedSurfaceProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichMeanBound() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val oldMeanBound = report.getMaxAllowedMeanTemperature();
        val oldMeanTemperature = report.getMeanTemperature();
        val oldThermocoupleTemperatures = report.getThermocoupleTemperatures();

        generalProperties.setEnvironmentTemperature(24);
        reportEnrichPipeline.accept(report, MAX_ALLOWED_MEAN_TEMPERATURE);

        val newMeanBound = report.getMaxAllowedMeanTemperature();
        val newMeanTemperature = report.getMeanTemperature();
        val newThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldMeanBound, newMeanBound);
        assertNotEquals(oldMeanTemperature, newMeanTemperature);
        assertNotEquals(oldThermocoupleTemperatures, newThermocoupleTemperatures);
    }

}
