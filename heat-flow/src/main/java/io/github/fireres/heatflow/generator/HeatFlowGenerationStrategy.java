package io.github.fireres.heatflow.generator;

import io.github.fireres.core.generator.strategy.FunctionsGenerationStrategy;

public class HeatFlowGenerationStrategy implements FunctionsGenerationStrategy {

    @Override
    public Integer resolveDelta(Integer t0, Integer time, Double coefficient) {
        return (int) ((Math.log(time + 2) / Math.log(1.005)) * coefficient);
    }

}
