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

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

public class FirstGroupTemperaturesEnrichTest extends AbstractTest {

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

        val oldFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val oldFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val newFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        assertNotEquals(oldFirstGroupMeanTemperature, newFirstGroupMeanTemperature);
        assertNotEquals(oldFirstGroupThermocoupleTemperatures, newFirstGroupThermocoupleTemperatures);
    }

}
