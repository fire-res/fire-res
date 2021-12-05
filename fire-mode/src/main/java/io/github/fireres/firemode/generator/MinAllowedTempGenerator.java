package io.github.fireres.firemode.generator;

import io.github.fireres.firemode.model.FurnaceTemperature;
import io.github.fireres.firemode.properties.Coefficient;
import io.github.fireres.firemode.properties.Coefficients;
import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.firemode.model.MinAllowedTemperature;
import io.github.fireres.core.model.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Slf4j
public class MinAllowedTempGenerator implements PointSequenceGenerator<MinAllowedTemperature> {

    private static final Coefficients COEFFICIENTS = new Coefficients(List.of(
            new Coefficient(0, 10, 0.85),
            new Coefficient(11, 30, 0.9),
            new Coefficient(31, Integer.MAX_VALUE, 0.95)
    ));

    private final FurnaceTemperature furnaceTemperature;

    @Override
    public MinAllowedTemperature generate() {
        if (furnaceTemperature.getValue().isEmpty()) {
            return new MinAllowedTemperature(emptyList());
        }

        val start = furnaceTemperature.getValue().get(0).getTime();
        val end = furnaceTemperature.getValue().get(furnaceTemperature.getValue().size() - 1).getTime();

        val minAllowedTemp = IntStream.range(start, end + 1)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(furnaceTemperature.getPoint(t).getValue() * COEFFICIENTS.getCoefficient(t))))
                .collect(Collectors.toList());

        return new MinAllowedTemperature(minAllowedTemp);
    }

}
