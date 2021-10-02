package io.github.fireres.core.utils;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.model.Point;
import lombok.val;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.RandomUtils.generateValueInInterval;

public class InterpolationUtils {

    private static final Random RANDOM = new Random();

    public static void addFirstPointIfNeeded(List<IntegerPoint> points, Integer t0) {
        val firstPoint = points.stream()
                .filter(point -> point.getTime().equals(0))
                .findFirst();

        if(firstPoint.isEmpty()) {
            val delta = RANDOM.nextInt(2) - 1;
            val newPoint = new IntegerPoint(0, t0 + delta);

            if (points.isEmpty()) {
                points.add(newPoint);
            } else {
                points.add(0, newPoint);
            }
        }
    }

    public static void addPointIfNeeded(List<IntegerPoint> points,
                                        Integer time,
                                        IntegerPointSequence lowerBound,
                                        IntegerPointSequence upperBound) {

        if(!pointExists(points, time)) {
            val closestPoint = points.stream()
                    .filter(p -> p.getTime() < time)
                    .max(Comparator.comparing(Point::getTime))
                    .orElseThrow();

            val min = Math.max(closestPoint.getValue(), lowerBound.getPoint(time).getValue());
            val max = upperBound.getPoint(time).getValue();

            val newPoint = new IntegerPoint(time, generateValueInInterval(min, max));

            points.add(points.size(), newPoint);
        }
    }

    private static boolean pointExists(List<IntegerPoint> points, Integer time) {
        return points.stream().anyMatch(point -> point.getTime().equals(time));
    }

    public static double[] getTimeArray(List<IntegerPoint> points) {
        val x = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            x[i] = point.getTime();
        }

        return x;
    }

    public static double[] getValueArray(List<IntegerPoint> points) {
        val y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            val point = points.get(i);
            y[i] = point.getValue();
        }

        return y;
    }

    public static List<IntegerPoint> interpolate(List<IntegerPoint> function) {
        val interpolator = new SplineInterpolator();
        val interpolation = interpolator.interpolate(
                getTimeArray(function),
                getValueArray(function));

        return IntStream.range(function.get(0).getTime(), function.get(function.size() - 1).getTime() + 1)
                .mapToObj(x -> new IntegerPoint(x, (int) Math.round(interpolation.value(x))))
                .collect(Collectors.toList());
    }

    public static Optional<Integer> lookUpClosestPreviousPoint(List<? extends Point<Integer>> interpolationPoints, int time) {
        return interpolationPoints.stream()
                .filter(point -> point.getTime() < time)
                .min((p1, p2) -> {
                    val p1Delta = time - p1.getTime();
                    val p2Delta = time - p2.getTime();

                    return p1Delta - p2Delta;
                })
                .map(Point::getValue);
    }

    public static Optional<Integer> lookUpClosestNextPoint(List<? extends Point<Integer>> interpolationPoints, int time) {
        return interpolationPoints.stream()
                .filter(point -> point.getTime() > time)
                .min((p1, p2) -> {
                    val p1Delta = p1.getTime() - time;
                    val p2Delta = p2.getTime() - time;

                    return p1Delta - p2Delta;
                })
                .map(Point::getValue);
    }
}
