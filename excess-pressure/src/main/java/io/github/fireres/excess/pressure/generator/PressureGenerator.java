package io.github.fireres.excess.pressure.generator;

import io.github.fireres.core.generator.Noise;
import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.excess.pressure.model.ExcessPressureMaxAllowedPressure;
import io.github.fireres.excess.pressure.model.ExcessPressureMinAllowedPressure;
import io.github.fireres.excess.pressure.model.ExcessPressure;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
public class PressureGenerator implements PointSequenceGenerator<ExcessPressure> {

    private final Integer time;

    private final ExcessPressureMinAllowedPressure minAllowedPressure;
    private final ExcessPressureMaxAllowedPressure maxAllowedPressure;

    private final Double dispersion;

    @Override
    public ExcessPressure generate() {
        val pressure = IntStream.range(0, time)
                .mapToObj(t -> {
                    val min = minAllowedPressure.getPoint(t).getValue();
                    val max = maxAllowedPressure.getPoint(t).getValue();
                    val seed = generateValueInInterval(0, Integer.MAX_VALUE - 1);

                    return new DoublePoint(t, Noise.noise(min, max, t, dispersion, seed));
                })
                .collect(Collectors.toList());

        return new ExcessPressure(pressure);
    }
}
