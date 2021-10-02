package io.github.fireres.core.model;

import io.github.fireres.core.generator.strategy.FunctionsGenerationStrategy;
import io.github.fireres.core.properties.FunctionForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionsGenerationParams {

    private FunctionForm<?> meanFunctionForm;

    private Integer childFunctionsCount;
    private FunctionsGenerationStrategy strategy;

    private FunctionsGenerationBounds meanBounds;
    private FunctionsGenerationBounds childrenBounds;

}
