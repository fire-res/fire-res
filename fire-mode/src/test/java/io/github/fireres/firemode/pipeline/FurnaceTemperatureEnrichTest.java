package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.report.FireModeReport;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotEquals;

public class FurnaceTemperatureEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichFurnaceTemperature() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = fireModeService.createReport(sample);

        val oldFurnaceTemperature = report.getFurnaceTemperature();

        generationProperties.getGeneral().setEnvironmentTemperature(24);

        reportEnrichPipeline.accept(report, FireModeReportEnrichType.FURNACE_TEMPERATURE);
        val newFurnaceTemperature = report.getFurnaceTemperature();

        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
    }

}
