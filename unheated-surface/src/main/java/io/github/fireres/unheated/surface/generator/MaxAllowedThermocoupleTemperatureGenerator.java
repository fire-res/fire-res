package io.github.fireres.unheated.surface.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.unheated.surface.model.MaxAllowedThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MaxAllowedThermocoupleTemperatureGenerator implements PointSequenceGenerator<MaxAllowedThermocoupleTemperature> {

    private final Integer time;
    private final Integer t0;

    @Override
    public MaxAllowedThermocoupleTemperature generate() {
        val thermocoupleBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 180 + t0))
                .collect(Collectors.toList());

        return new MaxAllowedThermocoupleTemperature(thermocoupleBound);
    }

}
