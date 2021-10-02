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

public class StandardTemperatureEnrichTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichStandardTemperature() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = fireModeService.createReport(sample);

        report.getProperties().getFunctionForm().getInterpolationPoints().clear();
        reportEnrichPipeline.accept(report);

        val oldMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val oldMinAllowedTemperature = report.getMinAllowedTemperature();
        val oldFurnaceTemperature = report.getFurnaceTemperature();
        val oldStandardTemperature = report.getStandardTemperature();
        val oldMeanTemperature = report.getThermocoupleMeanTemperature();
        val oldThermocoupleTemperatures = report.getThermocoupleTemperatures();

        generationProperties.getGeneral().setEnvironmentTemperature(24);
        reportEnrichPipeline.accept(report, FireModeReportEnrichType.STANDARD_TEMPERATURE);

        val newMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val newMinAllowedTemperature = report.getMinAllowedTemperature();
        val newFurnaceTemperature = report.getFurnaceTemperature();
        val newStandardTemperature = report.getStandardTemperature();
        val newMeanTemperature = report.getThermocoupleMeanTemperature();
        val newThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldMaxAllowedTemperature, newMaxAllowedTemperature);
        assertNotEquals(oldMinAllowedTemperature, newMinAllowedTemperature);
        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
        assertNotEquals(oldStandardTemperature, newStandardTemperature);
        assertNotEquals(oldMeanTemperature, newMeanTemperature);
        assertNotEquals(oldThermocoupleTemperatures, newThermocoupleTemperatures);
    }

}