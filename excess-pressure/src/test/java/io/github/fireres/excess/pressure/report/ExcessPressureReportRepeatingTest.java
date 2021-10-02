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
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.core.test.TestUtils.repeatTest;

public class ExcessPressureReportRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private ExcessPressureService excessPressureService;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = excessPressureService.createReport(sample);

            val min = report.getMinAllowedPressure();
            val max = report.getMaxAllowedPressure();
            val pressure = report.getPressure();

            assertFunctionIsConstant(-TestGenerationProperties.PRESSURE_DELTA, min.getValue());
            assertFunctionIsConstant(TestGenerationProperties.PRESSURE_DELTA, max.getValue());
            assertSizesEquals(TestGenerationProperties.TIME, min.getValue(), max.getValue());
            assertFunctionNotHigher(pressure.getValue(), max.getValue());
            assertFunctionNotLower(pressure.getValue(), min.getValue());
        });
    }

}
