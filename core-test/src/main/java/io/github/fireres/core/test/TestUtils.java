package io.github.fireres.core.test;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.model.PointSequence;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.MathUtils.calculatePointsMeanValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static final int TEST_ATTEMPTS = 1000;

    public static void assertFunctionIsConstant(Number expectedValue, List<? extends Point<?>> function) {
        function.forEach(p ->
                assertEquals(expectedValue.doubleValue(), p.getValue().doubleValue(), 0));
    }

    public static void assertFunctionConstantlyGrowing(List<? extends Point<?>> function) {
        for (int i = 1; i < function.size(); i++) {
            val point = function.get(i);
            val prevPoint = function.get(i - 1);

            assertTrue("Comparing " + prevPoint + " and " + point,
                    point.getValue().doubleValue() >= prevPoint.getValue().doubleValue());
        }
    }

    public static void assertChildTemperaturesEqualsMean(List<? extends PointSequence<?>> thermocouplesTemp,
                                                         PointSequence<?> meanTemp) {
        val meanTempValue = (List<Point>) meanTemp.getValue();

        assertSizesEquals(thermocouplesTemp, meanTempValue);

        for (int i = 0; i < meanTempValue.size(); i++) {
            val thermocouplePointIndex = i;

            val calculatedMean = calculatePointsMeanValue(thermocouplesTemp.stream()
                    .map(t -> t.getValue().get(thermocouplePointIndex))
                    .collect(Collectors.toList()));

            assertEquals(meanTempValue.get(i).getValue().doubleValue(), calculatedMean, 0.5);
        }
    }

    public static void assertSizesEquals(List<? extends PointSequence<?>> thermocouplesTemp, List<Point> meanTemp) {
        thermocouplesTemp.forEach(thermocoupleTemperature -> {
            val thermocoupleTemperatureValue = thermocoupleTemperature.getValue();
            assertEquals(meanTemp.size(), thermocoupleTemperatureValue.size());
        });
    }

    public static void assertSizesEquals(int size, List<? extends Point<?>>... functions) {
        for (List<? extends Point<?>> function : functions) {
            assertEquals(size, function.size());
        }
    }

    public static void assertFunctionNotHigher(List<? extends Point<?>> lowerFunction,
                                               List<? extends Point<?>> upperFunction) {

        lowerFunction.forEach(lowerPoint -> {
            val upperPoint = upperFunction.stream()
                    .filter(point -> point.getTime().equals(lowerPoint.getTime()))
                    .findFirst()
                    .orElseThrow();

            assertTrue("Lower: " + lowerPoint + " Upper: " + upperPoint,
                    lowerPoint.getValue().doubleValue() <= upperPoint.getValue().doubleValue());
        });
    }

    public static void assertFunctionNotLower(List<? extends Point<?>> upperFunction,
                                              List<? extends Point<?>> lowerFunction) {

        assertFunctionNotHigher(lowerFunction, upperFunction);
    }

    public static List<IntegerPoint> toPointList(List<Integer> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> new IntegerPoint(i, list.get(i)))
                .collect(Collectors.toList());
    }

}
