package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.firemode.config.Coefficient;
import io.github.therealmone.fireres.firemode.config.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.core.model.IntegerPoint;
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

    private final StandardTemperature standardTemp;

    @Override
    public MinAllowedTemperature generate() {
        if (standardTemp.getValue().isEmpty()) {
            return new MinAllowedTemperature(emptyList());
        }

        val start = standardTemp.getValue().get(0).getTime();
        val end = standardTemp.getValue().get(standardTemp.getValue().size() - 1).getTime();

        val minAllowedTemp = IntStream.range(start, end + 1)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(standardTemp.getPoint(t).getValue() * COEFFICIENTS.getCoefficient(t))))
                .collect(Collectors.toList());

        return new MinAllowedTemperature(minAllowedTemp);
    }

}
