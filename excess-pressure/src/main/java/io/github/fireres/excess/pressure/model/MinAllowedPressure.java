package io.github.fireres.excess.pressure.model;

import io.github.fireres.core.model.DoubleBound;
import io.github.fireres.core.model.DoublePoint;

import java.util.List;

public class MinAllowedPressure extends DoubleBound {
    public MinAllowedPressure(List<DoublePoint> value) {
        super(value);
    }
}
