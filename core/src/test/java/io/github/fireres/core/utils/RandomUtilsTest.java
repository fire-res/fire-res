package io.github.fireres.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomUtilsTest {

    private static final int CYCLES = 10000;

    @Test
    public void generateValueInInterval() {
        for (int i = 0; i < CYCLES; i++) {
            int generated = RandomUtils.generateValueInInterval(-100, 100);

            assertTrue(generated >= -100);
            assertTrue(generated <= 100);
        }
    }

    @Test
    public void generateValueInNegativeInterval() {
        for (int i = 0; i < CYCLES; i++) {
            int generated = RandomUtils.generateValueInInterval(-200, 0);

            assertTrue(generated >= -200);
            assertTrue(generated <= 0);
        }
    }

}
