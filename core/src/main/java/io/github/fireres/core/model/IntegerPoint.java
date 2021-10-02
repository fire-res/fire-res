package io.github.fireres.core.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IntegerPoint extends Point<Integer> {

    @Builder
    public IntegerPoint(Integer time, Integer value) {
        super(time, value);
    }

    @Override
    public Integer getNormalizedValue() {
        //already normalized
        return this.getValue();
    }

}
