package io.github.fireres.firemode.report;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.FireModeTestUtils;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.TEST_ATTEMPTS;
import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.firemode.config.TestConfig.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireModeReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private FireModeProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setup() {
        sample.removeAllReports();
        reportProperties.getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getBoundsShift().getMaxAllowedTemperatureShift().clear();
        reportProperties.getBoundsShift().getMinAllowedTemperatureShift().clear();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(10, -5));
        boundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(50, 100));

        boundsShift.getMinAllowedTemperatureShift().add(new IntegerPoint(10, 5));
        boundsShift.getMinAllowedTemperatureShift().add(new IntegerPoint(50, -100));
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {
        val report = fireModeService.createReport(sample, reportProperties);
        val boundsShift = report.getProperties().getBoundsShift();

        FireModeTestUtils.chooseNextFireModeType(report.getProperties());

        val furnaceTemp = report.getFurnaceTemperature().getValue();

        val minAllowedTemp =
                report.getMinAllowedTemperature()
                        .getShiftedValue(boundsShift.getMinAllowedTemperatureShift());

        val maxAllowedTemp =
                report.getMaxAllowedTemperature()
                        .getShiftedValue(boundsShift.getMaxAllowedTemperatureShift());

        val standardTemp = report.getStandardTemperature().getValue();

        //noinspection unchecked
        assertSizesEquals(TIME, furnaceTemp, minAllowedTemp, maxAllowedTemp, standardTemp);

        assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);

        assertFunctionNotLower(furnaceTemp, minAllowedTemp);
        assertFunctionNotHigher(furnaceTemp, maxAllowedTemp);

        val meanTemp = report.getThermocoupleMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
        assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);

        val thermocouplesTemps = report.getThermocoupleTemperatures();

        assertEquals(6, thermocouplesTemps.size());
        assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);

        });
    }

}
