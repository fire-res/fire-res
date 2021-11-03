package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.DoublePoint;
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
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.excess.pressure.config.TestConfig.TIME;

public class ExcessPressureReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ExcessPressureProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setUpBoundsShift() {
        sample.removeAllReports();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedPressureShift().clear();
        boundsShift.getMinAllowedPressureShift().clear();

        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(10, -0.5));
        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(30, 1.5));

        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(10, 0.5));
        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(30, -1.5));
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val min = report.getMinAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedPressureShift());

        val max = report.getMaxAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedPressureShift());

        val pressure = report.getPressure();

        assertSizesEquals(TIME, min, max);
        assertFunctionNotHigher(pressure.getValue(), max);
        assertFunctionNotLower(pressure.getValue(), min);
    }

}
