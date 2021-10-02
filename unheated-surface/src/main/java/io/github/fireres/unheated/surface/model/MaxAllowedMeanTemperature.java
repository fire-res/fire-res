package io.github.fireres.unheated.surface.model;

import io.github.fireres.core.model.IntegerBound;
import io.github.fireres.core.model.IntegerPoint;

import java.util.List;

public class MaxAllowedMeanTemperature extends IntegerBound {
    public MaxAllowedMeanTemperature(List<IntegerPoint> value) {
        super(value);
    }
}
