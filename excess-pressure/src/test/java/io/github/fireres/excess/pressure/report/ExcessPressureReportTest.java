package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.TestGenerationProperties;
import io.github.fireres.excess.pressure.config.TestConfig;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;

public class ExcessPressureReportTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private ExcessPressureService excessPressureService;

    @Test
    public void generateMinAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val minAllowedPressure = report.getMinAllowedPressure();

        assertFunctionIsConstant(-TestGenerationProperties.PRESSURE_DELTA, minAllowedPressure.getValue());
    }

    @Test
    public void generateMaxAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionIsConstant(TestGenerationProperties.PRESSURE_DELTA, maxAllowedPressure.getValue());
    }

    @Test
    public void generatePressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionNotHigher(report.getPressure().getValue(), maxAllowedPressure.getValue());
        assertFunctionNotLower(report.getPressure().getValue(), minAllowedPressure.getValue());
    }

}