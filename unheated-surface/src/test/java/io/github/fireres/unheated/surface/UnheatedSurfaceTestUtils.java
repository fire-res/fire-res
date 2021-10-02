package io.github.fireres.unheated.surface;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.List;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.TestGenerationProperties.TIME;
import static org.junit.Assert.assertEquals;

public class UnheatedSurfaceTestUtils {

    public static void assertUnheatedSurfaceReportIsValid(UnheatedSurfaceReport report) {
        assertUnheatedSurfaceGroup(
                report.getFirstGroup(),
                report.getFirstGroup().getMaxAllowedMeanTemperature()
                        .getShiftedValue(report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedMeanTemperatureShift()),
                report.getFirstGroup().getMaxAllowedThermocoupleTemperature()
                        .getShiftedValue(report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift()));

        assertUnheatedSurfaceGroup(
                report.getSecondGroup(),
                report.getSecondGroup().getMaxAllowedMeanTemperature()
                        .getShiftedValue(report.getProperties().getSecondGroup().getBoundsShift().getMaxAllowedTemperatureShift()),
                report.getSecondGroup().getMaxAllowedThermocoupleTemperature()
                        .getShiftedValue(report.getProperties().getSecondGroup().getBoundsShift().getMaxAllowedTemperatureShift()));

        assertUnheatedSurfaceGroup(
                report.getThirdGroup(),
                report.getThirdGroup().getMaxAllowedMeanTemperature()
                        .getShiftedValue(report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift()),
                report.getThirdGroup().getMaxAllowedThermocoupleTemperature()
                        .getShiftedValue(report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift()));
    }

    private static void assertUnheatedSurfaceGroup(Group group,
                                                   List<IntegerPoint> meanBound,
                                                   List<IntegerPoint> thermocoupleBound) {

        val meanTemp = group.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), constantFunction(0, TIME).getValue());

        //todo: bug
        //if (meanBound != null) {
        //    assertFunctionNotHigher(meanTemp.getValue(), meanBound);
        //} else {
        //    assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound);
        //}

        assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound);

        val thermocouplesTemps = group.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), constantFunction(0, TIME).getValue());
            assertFunctionNotHigher(thermocouplesTemp.getValue(), thermocoupleBound);
        });
    }
}
