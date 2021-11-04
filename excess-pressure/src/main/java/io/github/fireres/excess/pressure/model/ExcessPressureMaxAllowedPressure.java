package io.github.fireres.excess.pressure.model;

import io.github.fireres.core.model.DoubleBound;
import io.github.fireres.core.model.DoublePoint;

import java.util.List;

public class ExcessPressureMaxAllowedPressure extends DoubleBound {
    public ExcessPressureMaxAllowedPressure(List<DoublePoint> value) {
        super(value);
    }
}
