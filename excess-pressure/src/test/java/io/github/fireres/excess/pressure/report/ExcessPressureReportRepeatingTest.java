package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.TEST_ATTEMPTS;
import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.excess.pressure.config.TestConfig.PRESSURE_DELTA;
import static io.github.fireres.excess.pressure.config.TestConfig.TIME;

public class ExcessPressureReportRepeatingTest extends AbstractTest {

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ExcessPressureProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setup() {
        sample.removeAllReports();
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val min = report.getMinAllowedPressure();
        val max = report.getMaxAllowedPressure();
        val pressure = report.getPressure();

        assertFunctionIsConstant(-PRESSURE_DELTA, min.getValue());
        assertFunctionIsConstant(PRESSURE_DELTA, max.getValue());
        assertSizesEquals(TIME, min.getValue(), max.getValue());
        assertFunctionNotHigher(pressure.getValue(), max.getValue());
        assertFunctionNotLower(pressure.getValue(), min.getValue());
    }

}
