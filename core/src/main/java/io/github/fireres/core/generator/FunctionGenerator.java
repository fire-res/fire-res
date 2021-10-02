package io.github.fireres.core.generator;

import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.exception.FunctionGenerationException;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.utils.FunctionUtils;
import io.github.fireres.core.utils.InterpolationUtils;
import io.github.fireres.core.utils.RandomUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Slf4j
@Builder
public class FunctionGenerator implements PointSequenceGenerator<IntegerPointSequence> {

    private final Integer t0;
    private final Integer time;

    private final IntegerPointSequence upperBound;
    private final IntegerPointSequence lowerBound;

    private final FunctionForm<?> functionForm;

    @Override
    public IntegerPointSequence generate() {
        val points = functionForm.getInterpolationPoints().stream()
                .map(p -> new IntegerPoint(p.getTime(), p.getIntValue()))
                .collect(Collectors.toCollection(ArrayList::new));

        generateNecessaryPoints(points);
        adjustInterpolationPoints(points);

        if (functionForm.getLinearityCoefficient() != 1) {
            generateInnerPoints(points);
            adjustInterpolationPoints(points);
        }

        val thermocoupleMeanTemp = InterpolationUtils.interpolate(points);

        return new IntegerPointSequence(thermocoupleMeanTemp);
    }

    private void generateNecessaryPoints(List<IntegerPoint> points) {
        InterpolationUtils.addFirstPointIfNeeded(points, t0);
        InterpolationUtils.addPointIfNeeded(points, time - 1, lowerBound, upperBound);

        if (points.size() < 3) {
            InterpolationUtils.addPointIfNeeded(points, time / 2, lowerBound, upperBound);
        }

        points.sort(Comparator.comparing(Point::getTime));
    }

    private void adjustInterpolationPoints(List<IntegerPoint> points) {
        var function = InterpolationUtils.interpolate(points);

        while (isOutOfBounds(function) || notConstantlyGrowing(function)) {
            placeIntervalsInBounds(points, lookupIntervalsOutOfBounds(function));
            function = InterpolationUtils.interpolate(points);

            makeIntervalsConstantlyGrowing(function, points, lookupIntervalsNotConstantlyGrowing(function));
            function = InterpolationUtils.interpolate(points);
        }
    }

    private void placeIntervalsInBounds(List<IntegerPoint> points, List<Pair<IntegerPoint, IntegerPoint>> intervals) {
        intervals.forEach(interval -> {
            val middlePointTime = (int) Math.ceil((interval.getKey().getTime() + interval.getValue().getTime()) / 2d);

            generatePoint(points, middlePointTime);
        });
    }

    private void makeIntervalsConstantlyGrowing(List<IntegerPoint> function,
                                                List<IntegerPoint> points,
                                                List<Pair<IntegerPoint, IntegerPoint>> intervals) {
        intervals.forEach(interval -> {
            if (shouldAdjustLeftPoint(function, points, interval.getFirst())) {
                generatePoint(points, interval.getFirst().getTime());
            } else {
                generatePoint(points, interval.getSecond().getTime());
            }
        });
    }

    private boolean shouldAdjustLeftPoint(List<IntegerPoint> function, List<IntegerPoint> points, IntegerPoint point) {
        if (point.getTime().equals(0)) {
            return false;
        }

        val previous = function.get(point.getTime() - 1).getValue();
        val next = function.get(point.getTime() + 1).getValue();
        val nextInterpolationPoint = InterpolationUtils.lookUpClosestNextPoint(points, point.getTime());

        return (point.getValue() > previous && point.getValue() > next && next >= previous)
                || nextInterpolationPoint.isPresent() && point.getValue() > nextInterpolationPoint.get();
    }

    private void generatePoint(List<IntegerPoint> points, Integer time) {
        val previousPoint = InterpolationUtils.lookUpClosestPreviousPoint(points, time);
        val nextPoint = InterpolationUtils.lookUpClosestNextPoint(points, time);

        val max = nextPoint
                .map(point -> Math.min(point, upperBound.getPoint(time).getValue()))
                .orElse(upperBound.getPoint(time).getValue());

        val min = previousPoint
                .map(point -> Math.max(point, lowerBound.getPoint(time).getValue()))
                .orElse(upperBound.getPoint(time).getValue());

        if (min > max) {
            throw new FunctionGenerationException();
        }

        FunctionUtils.addPointOrAdjustExisting(points, min, max, time);
    }

    private List<Pair<IntegerPoint, IntegerPoint>> lookupIntervalsOutOfBounds(List<IntegerPoint> function) {
        val intervals = new ArrayList<Pair<IntegerPoint, IntegerPoint>>();

        IntegerPoint head = null;
        IntegerPoint tail = null;

        for (final IntegerPoint point : function) {
            if (isOutOfBounds(point)) {
                if (head == null) {
                    head = point;
                }

                tail = point;
            } else {
                if (tail != null) {
                    intervals.add(Pair.create(head, tail));
                    head = null;
                    tail = null;
                }
            }
        }

        if (tail != null) {
            intervals.add(Pair.create(head, tail));
        }

        return intervals;
    }

    private List<Pair<IntegerPoint, IntegerPoint>> lookupIntervalsNotConstantlyGrowing(List<IntegerPoint> function) {
        val intervals = new ArrayList<Pair<IntegerPoint, IntegerPoint>>();

        for (int i = 0; i < function.size() - 1; i++) {
            IntegerPoint point = function.get(i);
            IntegerPoint next = function.get(i + 1);

            if (point.getValue() > next.getValue()) {
                intervals.add(Pair.create(point, next));
                i += 1;
            }
        }

        return intervals;
    }

    private boolean notConstantlyGrowing(List<IntegerPoint> function) {
        for (int i = 0; i < function.size() - 1; i++) {
            val point = function.get(i);
            val next = function.get(i + 1);

            if (point.getValue() > next.getValue()) {
                return true;
            }
        }

        return false;
    }

    private boolean isOutOfBounds(List<IntegerPoint> function) {
        return function.stream().anyMatch(this::isOutOfBounds);
    }

    private boolean isOutOfBounds(IntegerPoint point) {
        val min = lowerBound.getPoint(point.getTime()).getValue();
        val max = upperBound.getPoint(point.getTime()).getValue();

        return point.getValue() < min || point.getValue() > max;
    }

    private void generateInnerPoints(List<IntegerPoint> points) {
        val function = InterpolationUtils.interpolate(points);

        function.stream()
                .filter(point -> !points.contains(point))
                .filter(point -> RandomUtils.rollDice(functionForm.getNonLinearityCoefficient()))
                .forEach(point -> {
                    val previous = InterpolationUtils.lookUpClosestPreviousPoint(points, point.getTime());
                    val next = InterpolationUtils.lookUpClosestNextPoint(points, point.getTime());

                    val lowerBoundValue = this.lowerBound.getPoint(point.getTime()).getValue();
                    val upperBoundValue = this.upperBound.getPoint(point.getTime()).getValue();

                    val min = previous.map(p -> Math.max(lowerBoundValue, p)).orElse(lowerBoundValue);
                    val max = next.map(p -> Math.min(upperBoundValue, p)).orElse(upperBoundValue);

                    val basisValue = point.getValue();

                    val minWithCoefficient = basisValue - (int) ((basisValue - min) * functionForm.getDispersionCoefficient());
                    val maxWithCoefficient = basisValue + (int) ((max - basisValue) * functionForm.getDispersionCoefficient());

                    if (minWithCoefficient > maxWithCoefficient) {
                        return;
                    }

                    points.add(new IntegerPoint(point.getTime(), RandomUtils.generateValueInInterval(minWithCoefficient, maxWithCoefficient)));
                });

        points.sort(Comparator.comparing(Point::getTime));
    }

}
