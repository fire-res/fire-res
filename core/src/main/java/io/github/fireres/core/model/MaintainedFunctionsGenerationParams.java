package io.github.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintainedFunctionsGenerationParams {

    private Integer temperature;
    private Integer tStart;
    private Integer tEnd;

    private FunctionsGenerationBounds bounds;

    private Integer functionsCount;

}
