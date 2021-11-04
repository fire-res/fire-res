package io.github.fireres.excess.pressure.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.excess.pressure.model.ExcessPressureMaxAllowedPressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MaxAllowedPressureGenerator implements PointSequenceGenerator<ExcessPressureMaxAllowedPressure> {

    private final Integer time;
    private final Double delta;

    @Override
    public ExcessPressureMaxAllowedPressure generate() {
        val maxAllowedPressure = IntStream.range(0, time)
                .mapToObj(t -> new DoublePoint(t, delta))
                .collect(Collectors.toList());

        return new ExcessPressureMaxAllowedPressure(maxAllowedPressure);
    }
}
