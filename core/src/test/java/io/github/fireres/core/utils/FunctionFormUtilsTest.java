package io.github.fireres.core.utils;

import io.github.fireres.core.model.IntegerPoint;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FunctionFormUtilsTest {

    @Test
    public void addZeroPoint() {
        val points = new ArrayList<IntegerPoint>();
        InterpolationUtils.addFirstPointIfNeeded(points, 2);

        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(2, points.get(0).getValue(), 1);
    }

    @Test
    public void addZeroPointInNonEmptyList() {
        val points = new ArrayList<IntegerPoint>() {{
                add(new IntegerPoint(1, 1));
                add(new IntegerPoint(2, 2));
                add(new IntegerPoint(3, 3));
        }};

        InterpolationUtils.addFirstPointIfNeeded(points, 0);

        assertEquals(4, points.size());
        assertEquals(0, points.get(0).getTime(), 0);
        assertEquals(0, points.get(0).getValue(), 1);
    }

}