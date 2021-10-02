package io.github.fireres.firemode.model;

import io.github.fireres.core.model.IntegerBound;
import io.github.fireres.core.model.IntegerPoint;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MinAllowedTemperature extends IntegerBound {
    @Builder
    public MinAllowedTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
