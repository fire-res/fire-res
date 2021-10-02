package io.github.fireres.core.model;

import io.github.fireres.core.utils.FunctionUtils;

import java.util.List;

public class IntegerBound extends IntegerPointSequence {
    public IntegerBound(List<IntegerPoint> value) {
        super(value);
    }

    public List<IntegerPoint> getShiftedValue(BoundShift<IntegerPoint> shift) {
        return FunctionUtils.calculateShiftedIntegerPoints(this.getValue(), shift);
    }
}
