package io.github.fireres.firemode.generator;

import io.github.fireres.firemode.model.StandardTemperature;
import io.github.fireres.firemode.properties.Coefficient;
import io.github.fireres.firemode.properties.Coefficients;
import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.firemode.model.MaxAllowedTemperature;
import io.github.fireres.core.model.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Slf4j
public class MaxAllowedTempGenerator implements PointSequenceGenerator<MaxAllowedTemperature> {

    private static final Coefficients COEFFICIENTS = new Coefficients(List.of(
            new Coefficient(0, 10, 1.15),
            new Coefficient(11, 30, 1.1),
            new Coefficient(31, Integer.MAX_VALUE, 1.05)
    ));

    private final StandardTemperature standardTemperature;
    @Setter
    private Coefficients customCoefficients = COEFFICIENTS;

    @Override
    public MaxAllowedTemperature generate() {
        if (standardTemperature.getValue().isEmpty()) {
            return new MaxAllowedTemperature(emptyList());
        }

        val start = standardTemperature.getValue().get(0).getTime();
        val end = standardTemperature.getValue().get(standardTemperature.getValue().size() - 1).getTime();

        val maxAllowedTemp = IntStream.range(start, end + 1)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(standardTemperature.getPoint(t).getValue() * customCoefficients.getCoefficient(t))))
                .collect(Collectors.toList());

        return new MaxAllowedTemperature(maxAllowedTemp);
    }

}
