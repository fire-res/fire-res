package io.github.fireres.firemode.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.firemode.model.FurnaceTemperature;
import io.github.fireres.firemode.model.StandardTemperature;
import io.github.fireres.core.model.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class FurnaceTempGenerator implements PointSequenceGenerator<FurnaceTemperature> {

    private final Integer t0;
    private final StandardTemperature standardTemp;

    @Override
    public FurnaceTemperature generate() {
        val start = standardTemp.getValue().get(0).getTime();
        val end = standardTemp.getValue().get(standardTemp.getValue().size() - 1).getTime();

        val furnaceTemp = IntStream.range(start, end + 1)
                .mapToObj(t -> new IntegerPoint(t,
                        t0 + standardTemp.getPoint(t).getValue()))
                .collect(Collectors.toList());

        return new FurnaceTemperature(furnaceTemp);
    }

}
