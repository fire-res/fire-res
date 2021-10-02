package io.github.fireres.firemode.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.firemode.model.StandardTemperature;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.firemode.model.FireModeType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
@Builder
public class StandardTempGenerator implements PointSequenceGenerator<StandardTemperature> {

    private final Integer t0;
    private final Integer time;
    private final FireModeType fireModeType;

    @Override
    public StandardTemperature generate() {
        val standardTemp = IntStream.range(1, time)
                .mapToObj(t -> new IntegerPoint(t, fireModeType.getFunction().apply(t)))
                .collect(Collectors.toList());

        standardTemp.add(0, new IntegerPoint(0, t0));

        return new StandardTemperature(standardTemp);
    }

}
