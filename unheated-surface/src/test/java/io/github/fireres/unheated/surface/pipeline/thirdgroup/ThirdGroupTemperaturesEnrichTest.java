package io.github.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

public class ThirdGroupTemperaturesEnrichTest extends AbstractTest {

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

        val oldThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val oldThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val newThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        assertNotEquals(oldThirdGroupMeanTemperature, newThirdGroupMeanTemperature);
        assertNotEquals(oldThirdGroupThermocoupleTemperatures, newThirdGroupThermocoupleTemperatures);
    }

}
