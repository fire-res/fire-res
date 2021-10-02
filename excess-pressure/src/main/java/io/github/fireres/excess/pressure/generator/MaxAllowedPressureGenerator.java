package io.github.fireres.excess.pressure.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.excess.pressure.model.MaxAllowedPressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MaxAllowedPressureGenerator implements PointSequenceGenerator<MaxAllowedPressure> {

    private final Integer time;
    private final Double delta;

    @Override
    public MaxAllowedPressure generate() {
        val maxAllowedPressure = IntStream.range(0, time)
                .mapToObj(t -> new DoublePoint(t, delta))
                .collect(Collectors.toList());

        return new MaxAllowedPressure(maxAllowedPressure);
    }
}
