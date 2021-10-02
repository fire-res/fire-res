package io.github.fireres.core.utils;

import io.github.fireres.core.model.Point;
import lombok.val;

import java.util.List;

public class MathUtils {

    public static Integer calculatePointsMeanValue(List<? extends Point<?>> points) {
        val sum = points.stream()
                .map(point -> point.getValue().doubleValue())
                .reduce(0d, Double::sum);

        return (int) Math.round(sum / (double) points.size());
    }

    public static Integer calculateIntsMeanValue(List<Integer> ints) {
        val sum = ints.stream().reduce(0, Integer::sum);

        return (int) Math.round(sum / (double) ints.size());
    }
}
