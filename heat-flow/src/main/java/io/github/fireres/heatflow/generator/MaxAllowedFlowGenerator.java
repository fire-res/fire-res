package io.github.fireres.heatflow.generator;

import io.github.fireres.core.generator.PointSequenceGenerator;
import io.github.fireres.heatflow.model.MaxAllowedFlow;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MaxAllowedFlowGenerator implements PointSequenceGenerator<MaxAllowedFlow> {

    private final Integer time;
    private final Double bound;

    @Override
    public MaxAllowedFlow generate() {
        return new MaxAllowedFlow(IntStream.range(0, time)
                .mapToObj(t -> new HeatFlowPoint(t, bound))
                .collect(Collectors.toList()));
    }

}
