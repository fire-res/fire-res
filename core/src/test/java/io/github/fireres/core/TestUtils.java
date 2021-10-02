package io.github.fireres.core;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Point;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionConstantlyGrowing(List<IntegerPoint> function) {
        for (int i = 1; i < function.size(); i++) {
            val point = function.get(i);
            val prevPoint = function.get(i - 1);

            assertTrue("Comparing " + prevPoint + " and " + point,
                    point.getValue() >= prevPoint.getValue());
        }
    }

    public static List<IntegerPoint> toPointList(List<Integer> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> new IntegerPoint(i, list.get(i)))
                .collect(Collectors.toList());
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
}
