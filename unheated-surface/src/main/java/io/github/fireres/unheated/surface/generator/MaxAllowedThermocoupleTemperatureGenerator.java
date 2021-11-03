package io.github.fireres.unheated.surface.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.unheated.surface.model.MaxAllowedThermocoupleTemperature;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.PRIMARY;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.SECONDARY;

@RequiredArgsConstructor
public class MaxAllowedThermocoupleTemperatureGenerator implements PointSequenceGenerator<MaxAllowedThermocoupleTemperature> {

    private final Integer time;
    private final Integer t0;
    private final Integer bound;
    private final UnheatedSurfaceType type;

    @Override
    public MaxAllowedThermocoupleTemperature generate() {
        if (type == PRIMARY) {
            return generateForPrimaryType();
        } else if (type == SECONDARY) {
            return generateForSecondaryType();
        }

        throw new IllegalArgumentException("Invalid report type: " + type);
    }

    private MaxAllowedThermocoupleTemperature generateForPrimaryType() {
        val thermocoupleBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 180 + t0))
                .collect(Collectors.toList());

        return new MaxAllowedThermocoupleTemperature(thermocoupleBound);
    }

    private MaxAllowedThermocoupleTemperature generateForSecondaryType() {
        return new MaxAllowedThermocoupleTemperature(constantFunction(time, bound).getValue());
    }

}
