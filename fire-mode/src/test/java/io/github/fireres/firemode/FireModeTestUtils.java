package io.github.fireres.firemode;

import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.model.FireModeType;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Arrays;

@UtilityClass
public class FireModeTestUtils {

    public static void chooseNextFireModeType(FireModeProperties properties) {
        val types = FireModeType.values();

        val current = properties.getFireModeType();
        val currentIndex = Arrays.asList(types).indexOf(current);

        if (currentIndex == types.length - 1) {
            properties.setFireModeType(types[0]);
        } else {
            properties.setFireModeType(types[currentIndex + 1]);
        }
    }

}
