package io.github.fireres.unheated.surface.pipeline.firstgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

public class FirstGroupMeanBoundEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Test
    public void enrichFirstGroupMeanBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldFirstGroupMeanBound = report.getFirstGroup().getMaxAllowedMeanTemperature();
        val oldFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val oldFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        generationProperties.getGeneral().setEnvironmentTemperature(24);
        reportEnrichPipeline.accept(report, FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE);

        val newFirstGroupMeanBound = report.getFirstGroup().getMaxAllowedMeanTemperature();
        val newFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val newFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        assertNotEquals(oldFirstGroupMeanBound, newFirstGroupMeanBound);
        assertNotEquals(oldFirstGroupMeanTemperature, newFirstGroupMeanTemperature);
        assertNotEquals(oldFirstGroupThermocoupleTemperatures, newFirstGroupThermocoupleTemperatures);
    }

}
