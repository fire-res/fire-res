package io.github.fireres.heatflow.model;

import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.heatflow.service.impl.NormalizationServiceImpl;

public class HeatFlowPoint extends DoublePoint {

    public HeatFlowPoint(Integer time, Double value) {
        super(time, value);
    }

    @Override
    public Double getNormalizedValue() {
        return ((int) (this.getValue() * 100)) / 100d;
    }

    @Override
    public Integer getIntValue() {
        return (int) (getValue() * NormalizationServiceImpl.NORMALIZATION_MULTIPLIER);
    }
}
