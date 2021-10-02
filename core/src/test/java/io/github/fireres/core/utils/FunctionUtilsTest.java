package io.github.fireres.core.utils;

import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.IntegerPoint;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunctionUtilsTest {

    @Test
    public void calculateShiftedPoints() {
        val bound = List.of(
                new IntegerPoint(1, 1),
                new IntegerPoint(2, 2),
                new IntegerPoint(3, 3),
                new IntegerPoint(4, 4)
        );

        val shift = new BoundShift<IntegerPoint>();

        shift.add(new IntegerPoint(2, 5));
        shift.add(new IntegerPoint(4, 15));

        val shiftedBound = FunctionUtils.calculateShiftedIntegerPoints(bound, shift);

        assertEquals(shiftedBound, List.of(
                new IntegerPoint(1, 1),
                new IntegerPoint(2, 7),
                new IntegerPoint(3, 8),
                new IntegerPoint(4, 19)
        ));
    }

    @Test
    public void calculateNegativelyShiftedPoints() {
        val bound = List.of(
                new IntegerPoint(1, 1),
                new IntegerPoint(2, 2),
                new IntegerPoint(3, 3),
                new IntegerPoint(4, 4)
        );

        val shift = new BoundShift<IntegerPoint>();

        shift.add(new IntegerPoint(2, -5));
        shift.add(new IntegerPoint(4, -15));

        val shiftedBound = FunctionUtils.calculateShiftedIntegerPoints(bound, shift);

        assertEquals(shiftedBound, List.of(
                new IntegerPoint(1, 1),
                new IntegerPoint(2, -3),
                new IntegerPoint(3, -2),
                new IntegerPoint(4, -11)
        ));
    }

}
