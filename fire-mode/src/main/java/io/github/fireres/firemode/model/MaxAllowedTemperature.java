package io.github.fireres.firemode.model;

import io.github.fireres.core.model.IntegerBound;
import io.github.fireres.core.model.IntegerPoint;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MaxAllowedTemperature extends IntegerBound {
    @Builder
    public MaxAllowedTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
