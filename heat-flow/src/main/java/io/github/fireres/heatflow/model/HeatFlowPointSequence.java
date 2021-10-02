package io.github.fireres.heatflow.model;

import io.github.fireres.core.model.PointSequence;

import java.util.List;

public class HeatFlowPointSequence extends PointSequence<HeatFlowPoint> {
    public HeatFlowPointSequence(List<HeatFlowPoint> value) {
        super(value);
    }
}
