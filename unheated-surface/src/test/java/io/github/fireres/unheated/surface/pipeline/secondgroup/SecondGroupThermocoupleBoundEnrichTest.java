package io.github.fireres.unheated.surface.pipeline.secondgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MAX_ALLOWED_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

public class SecondGroupThermocoupleBoundEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldSecondGroupThermocoupleBound = report.getSecondGroup().getMaxAllowedThermocoupleTemperature();
        val oldSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        report.getProperties().getSecondGroup().setBound(400);
        reportEnrichPipeline.accept(report, SECOND_GROUP_MAX_ALLOWED_TEMPERATURE);

        val newSecondGroupThermocoupleBound = report.getSecondGroup().getMaxAllowedThermocoupleTemperature();
        val newSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupThermocoupleBound, newSecondGroupThermocoupleBound);
        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
