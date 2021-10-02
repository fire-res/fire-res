package io.github.fireres.core.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DoublePoint extends Point<Double> {

    @Builder
    public DoublePoint(Integer time, Double value) {
        super(time, value);
    }

    @Override
    public Double getNormalizedValue() {
        return ((int) (this.getValue() * 100)) / 100d;
    }

}
