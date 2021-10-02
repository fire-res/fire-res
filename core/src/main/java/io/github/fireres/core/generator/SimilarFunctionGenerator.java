package io.github.fireres.core.generator;

import io.github.fireres.core.exception.ImpossibleGenerationException;
import io.github.fireres.core.generator.strategy.FunctionsGenerationStrategy;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.utils.InterpolationUtils;
import io.github.fireres.core.properties.FunctionForm;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Comparator;
import java.util.stream.Collectors;

import static io.github.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Builder
public class SimilarFunctionGenerator implements PointSequenceGenerator<IntegerPointSequence> {

    private static final int INTERPOLATION_POINTS_INTERVAL = 10;

    private final Integer t0;
    private final Integer time;

    private final IntegerPointSequence basis;

    private final IntegerPointSequence lowerBound;
    private final IntegerPointSequence upperBound;

    private final FunctionsGenerationStrategy functionsGenerationStrategy;

    private final FunctionForm<?> originalForm;

    @Override
    public IntegerPointSequence generate() {
        val similarFunctionForm = generateFunctionForm();

        return FunctionGenerator.builder()
                .time(time)
                .t0(t0)
                .upperBound(upperBound)
                .lowerBound(lowerBound)
                .functionForm(similarFunctionForm)
                .build()
                .generate();
    }

    private FunctionForm<Integer> generateFunctionForm() {
        val newFunctionForm = new FunctionForm<Integer>();

        newFunctionForm.setInterpolationPoints(originalForm.getInterpolationPoints().stream()
                .map(point -> new IntegerPoint(point.getTime(), point.getIntValue()))
                .collect(Collectors.toList()));

        newFunctionForm.setLinearityCoefficient(originalForm.getLinearityCoefficient());
        newFunctionForm.setDispersionCoefficient(originalForm.getDispersionCoefficient());

        for (int i = 0; i < basis.getValue().size(); i += INTERPOLATION_POINTS_INTERVAL) {
            if (!containsInterpolationPoint(newFunctionForm, i)) {
                val time = i;

                val previousPoint = InterpolationUtils.lookUpClosestPreviousPoint(newFunctionForm.getInterpolationPoints(), i);
                val nextPoint = InterpolationUtils.lookUpClosestNextPoint(newFunctionForm.getInterpolationPoints(), i);

                val delta = functionsGenerationStrategy.resolveDelta(t0, i, originalForm.getChildFunctionsDeltaCoefficient());

                val basisValue = nextPoint
                        .map(p -> Math.min(basis.getPoint(time).getValue(), p))
                        .orElse(basis.getPoint(time).getValue());

                val lowerBoundValue = this.lowerBound.getPoint(i).getValue();
                val upperBoundValue = this.upperBound.getPoint(i).getValue();

                val localLowerBound = previousPoint.map(point -> Math.max(lowerBoundValue, point)).orElse(lowerBoundValue);
                val localUpperBound = nextPoint.map(point -> Math.min(upperBoundValue, point)).orElse(upperBoundValue);

                if (basisValue < localLowerBound) {
                    newFunctionForm.getInterpolationPoints().add(new IntegerPoint(i, localLowerBound));
                } else {
                    val min = Math.max(basisValue - delta, localLowerBound);
                    val max = Math.min(basisValue + delta, localUpperBound);

                    if (min > max) {
                        throw new ImpossibleGenerationException();
                    }

                    newFunctionForm.getInterpolationPoints().add(new IntegerPoint(i, generateValueInInterval(min, max)));
                }
            }
        }

        newFunctionForm.getInterpolationPoints().sort(Comparator.comparing(Point::getTime));

        return newFunctionForm;
    }

    private boolean containsInterpolationPoint(FunctionForm<?> newFunctionForm, int time) {
        return newFunctionForm.getInterpolationPoints().stream()
                .anyMatch(point -> point.getTime().equals(time));
    }
}
