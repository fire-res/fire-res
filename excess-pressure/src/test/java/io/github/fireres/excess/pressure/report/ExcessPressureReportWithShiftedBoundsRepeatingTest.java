package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.TestGenerationProperties;
import io.github.fireres.excess.pressure.config.TestConfig;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.core.test.TestUtils.repeatTest;

public class ExcessPressureReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private ExcessPressureService excessPressureService;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(ExcessPressureProperties.class)
                .orElseThrow();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(10, -0.5));
        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(30, 1.5));

        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(10, 0.5));
        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(30, -1.5));
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = excessPressureService.createReport(sample);

            val min = report.getMinAllowedPressure()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedPressureShift());

            val max = report.getMaxAllowedPressure()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedPressureShift());

            val pressure = report.getPressure();

            assertSizesEquals(TestGenerationProperties.TIME, min, max);
            assertFunctionNotHigher(pressure.getValue(), max);
            assertFunctionNotLower(pressure.getValue(), min);
        });
    }

}
