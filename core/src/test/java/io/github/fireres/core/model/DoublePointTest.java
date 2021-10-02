package io.github.fireres.core.model;

import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoublePointTest {

    @Test
    public void normalizeWithPositiveShift() {
        val doublePoint = new DoublePoint(0,1.2345);

        assertEquals(1.23, doublePoint.getNormalizedValue(), 0);
    }

    @Test
    public void normalizeWithNegativeShift() {
        val doublePoint = new DoublePoint(0,1234.5);

        assertEquals(1234.50, doublePoint.getNormalizedValue(), 0);
    }

    @Test
    public void normalizeWithPositiveShiftAndIntValue() {
        val doublePoint = new DoublePoint(0,12345.0);

        assertEquals(12345.00, doublePoint.getNormalizedValue(), 0);
    }

    @Test
    public void normalizeWithNegativeShiftAndLowValue() {
        val doublePoint = new DoublePoint(0,1.2345);

        assertEquals(1.23, doublePoint.getNormalizedValue(), 0);
    }

}