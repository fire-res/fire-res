package io.github.fireres.unheated.surface.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MaxAllowedMeanTemperatureGenerator implements PointSequenceGenerator<MaxAllowedMeanTemperature> {

    private final Integer time;
    private final Integer t0;

    @Override
    public MaxAllowedMeanTemperature generate() {
        val meanBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 140 + t0))
                .collect(Collectors.toList());

        return new MaxAllowedMeanTemperature(meanBound);
    }

}
