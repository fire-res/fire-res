package io.github.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionsGenerationBounds {

    private IntegerPointSequence lowerBound;
    private IntegerPointSequence upperBound;

}
