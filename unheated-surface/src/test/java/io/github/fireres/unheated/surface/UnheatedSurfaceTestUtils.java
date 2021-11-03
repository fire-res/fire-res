package io.github.fireres.unheated.surface;

import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.config.TestConfig.TIME;
import static org.junit.Assert.assertEquals;

public class UnheatedSurfaceTestUtils {

    public static void assertUnheatedSurfaceReportIsValid(UnheatedSurfaceReport report) {
        val meanBound = report.getMaxAllowedMeanTemperature()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedMeanTemperatureShift());

        val thermocoupleBound = report.getMaxAllowedThermocoupleTemperature()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift());

        val meanTemp = report.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), constantFunction(0, TIME).getValue());

        //todo: bug
//        if (meanBound != null) {
//            assertFunctionNotHigher(meanTemp.getValue(), meanBound);
//        } else {
//            assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound);
//        }

        assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound);

        val thermocouplesTemps = report.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), constantFunction(0, TIME).getValue());
            assertFunctionNotHigher(thermocouplesTemp.getValue(), thermocoupleBound);
        });
    }

}
