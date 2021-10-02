package io.github.fireres.core.utils;

import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.model.Point;
import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.RandomUtils.generateValueInInterval;

public class FunctionUtils {

    public static void addPointOrAdjustExisting(List<IntegerPoint> points, Integer min, Integer max, Integer time) {
        val newPointValue = RandomUtils.generateValueInInterval(min, max);
        val existingPoint = lookupPoint(points, time);

        if (existingPoint.isPresent()) {
            existingPoint.get().setValue(newPointValue);
        } else {
            points.add(new IntegerPoint(time, newPointValue));
            points.sort(Comparator.comparing(Point::getTime));
        }
    }

    public static Optional<IntegerPoint> lookupPoint(List<IntegerPoint> points, Integer time) {
        return points.stream()
                .filter(point -> point.getTime().equals(time))
                .findFirst();
    }

    public static IntegerPointSequence constantFunction(Integer time, Integer value) {
        return constantFunction(0, time, value);
    }

    public static IntegerPointSequence constantFunction(Integer tStart, Integer tEnd, Integer value) {
        return new IntegerPointSequence(IntStream.range(tStart, tEnd)
                .mapToObj(t -> new IntegerPoint(t, value))
                .collect(Collectors.toList()));
    }

    public static List<IntegerPoint> calculateShiftedIntegerPoints(List<IntegerPoint> original,
                                                                   BoundShift<IntegerPoint> shift) {

        return calculateShiftedPoints(
                original, shift,
                (p1, p2) -> p1.getValue() + p2.getValue(),
                IntegerPoint::new);
    }

    public static List<DoublePoint> calculateShiftedDoublePoints(List<DoublePoint> original,
                                                                 BoundShift<DoublePoint> shift) {

        return calculateShiftedPoints(
                original, shift,
                (p1, p2) -> p1.getValue() + p2.getValue(),
                DoublePoint::new);
    }

    public static <N extends Number, P extends Point<N>>
    List<P> calculateShiftedPoints(List<P> original,
                                   BoundShift<P> shift,
                                   BiFunction<P, P, N> shiftedValueCalculator,
                                   BiFunction<Integer, N, P> newPointCreator) {

        val shiftedPoints = new ArrayList<P>();

        for (P originalPoint : original) {
            val pointShift = lookupShiftForPoint(originalPoint, shift);

            if (pointShift.isPresent()) {
                val shiftedValue = shiftedValueCalculator.apply(originalPoint, pointShift.get());
                shiftedPoints.add(newPointCreator.apply(originalPoint.getTime(), shiftedValue));
            } else {
                shiftedPoints.add(newPointCreator.apply(originalPoint.getTime(), originalPoint.getValue()));
            }
        }

        return shiftedPoints;
    }

    private static <P extends Point<?>> Optional<P> lookupShiftForPoint(P originalPoint, BoundShift<P> shift) {
        return shift.getPoints().stream()
                .filter(s -> s.getTime() <= originalPoint.getTime())
                .min(Comparator.comparingInt(s -> (originalPoint.getTime() - s.getTime())));
    }

    public static IntegerPointSequence calculateMeanFunction(List<? extends IntegerPointSequence> childFunctions) {
        if (childFunctions == null || childFunctions.isEmpty()) {
            throw new IllegalArgumentException();
        }

        val meanFunction = new ArrayList<IntegerPoint>();

        for (int i = 0; i < childFunctions.get(0).getValue().size(); i++) {
            val pointIndex = i;
            val points = childFunctions.stream()
                    .map(childFunction -> childFunction.getValue().get(pointIndex))
                    .collect(Collectors.toList());

            meanFunction.add(new IntegerPoint(
                    childFunctions.get(0).getValue().get(i).getTime(),
                    MathUtils.calculatePointsMeanValue(points)));
        }

        return new IntegerPointSequence(meanFunction);
    }

    public static IntegerPointSequence merge(IntegerPointSequence to, IntegerPointSequence from) {
        val toCopy = copy(to);

        for (IntegerPoint fromPoint : from.getValue()) {
            toCopy.getPoint(fromPoint.getTime()).setValue(fromPoint.getValue());
        }

        return toCopy;
    }

    private static IntegerPointSequence copy(IntegerPointSequence to) {
        val copy = new ArrayList<IntegerPoint>();

        for (IntegerPoint point : to.getValue()) {
            copy.add(new IntegerPoint(point.getTime(), point.getValue()));
        }

        return new IntegerPointSequence(copy);
    }

}
