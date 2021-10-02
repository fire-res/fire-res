package io.github.fireres.heatflow.model;

import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.utils.FunctionUtils;

import java.util.List;

public class HeatFlowBound extends HeatFlowPointSequence {
    public HeatFlowBound(List<HeatFlowPoint> value) {
        super(value);
    }

    public List<HeatFlowPoint> getShiftedValue(BoundShift<HeatFlowPoint> shift) {
        return FunctionUtils.calculateShiftedPoints(
                this.getValue(), shift,
                (p1, p2) -> p1.getValue() + p2.getValue(),
                HeatFlowPoint::new);
    }
}
