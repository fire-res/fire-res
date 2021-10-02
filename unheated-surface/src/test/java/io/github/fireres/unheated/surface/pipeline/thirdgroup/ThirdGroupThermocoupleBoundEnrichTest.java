package io.github.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotEquals;

public class ThirdGroupThermocoupleBoundEnrichTest extends AbstractTest {

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

        val oldThirdGroupThermocoupleBound = report.getThirdGroup().getMaxAllowedThermocoupleTemperature();
        val oldThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val oldThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        report.getProperties().getThirdGroup().setBound(400);
        reportEnrichPipeline.accept(report, UnheatedSurfaceReportEnrichType.THIRD_GROUP_MAX_ALLOWED_TEMPERATURE);

        val newThirdGroupThermocoupleBound = report.getThirdGroup().getMaxAllowedThermocoupleTemperature();
        val newThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val newThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        assertNotEquals(oldThirdGroupThermocoupleBound, newThirdGroupThermocoupleBound);
        assertNotEquals(oldThirdGroupMeanTemperature, newThirdGroupMeanTemperature);
        assertNotEquals(oldThirdGroupThermocoupleTemperatures, newThirdGroupThermocoupleTemperatures);
    }

}
