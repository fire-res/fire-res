package io.github.fireres.unheated.surface.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.PRIMARY;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.SECONDARY;

@RequiredArgsConstructor
public class MaxAllowedMeanTemperatureGenerator implements PointSequenceGenerator<MaxAllowedMeanTemperature> {

    private final Integer time;
    private final Integer t0;
    private final Integer bound;
    private final UnheatedSurfaceType type;

    @Override
    public MaxAllowedMeanTemperature generate() {
        if (type == PRIMARY) {
            return generateForPrimaryType();
        } else if (type == SECONDARY) {
            return generateForSecondaryType();
        }

        throw new IllegalArgumentException("Invalid report type: " + type);
    }

    private MaxAllowedMeanTemperature generateForPrimaryType() {
        val meanBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 140 + t0))
                .collect(Collectors.toList());

        return new MaxAllowedMeanTemperature(meanBound);
    }

    private MaxAllowedMeanTemperature generateForSecondaryType() {
        return new MaxAllowedMeanTemperature(constantFunction(time, bound).getValue());
    }

}
