package io.github.fireres.unheated.surface.pipeline.secondgroup;

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

public class SecondGroupTemperaturesEnrichTest extends AbstractTest {

    @Autowired
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private GenerationProperties generationProperties;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
