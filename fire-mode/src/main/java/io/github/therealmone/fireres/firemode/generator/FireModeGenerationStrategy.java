package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.core.generator.strategy.FunctionsGenerationStrategy;

public class FireModeGenerationStrategy implements FunctionsGenerationStrategy {

    @Override
    public Integer resolveDelta(Integer t0, Integer time, Double coefficient) {
        return (int) ((Math.log(time + 1) / Math.log(1.05)) * coefficient);
    }

}
