package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.report.FireModeReport;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotEquals;

public class MeanWithThermocoupleTemperaturesEnrichTest extends AbstractTest {

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Autowired
    private FireModeProperties reportProperties;

    @Autowired
    private Sample sample;

    @Test
    public void enrichSamplePressure() {
        val report = fireModeService.createReport(sample, reportProperties);

        val oldSampleMeanTemperature = report.getThermocoupleMeanTemperature();
        val oldSampleThermocoupleTemperatures = report.getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        val newSampleMeanTemperature = report.getThermocoupleMeanTemperature();
        val newSampleThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldSampleMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSampleThermocoupleTemperatures, newSampleThermocoupleTemperatures);
    }

}
