package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.NumberSequenceGeneratorFactory;
import lombok.val;
import org.junit.Test;

import javax.validation.ValidationException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StandardTempGeneratorTest {

    @Test
    public void generateTest() {
        val generator = new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .t0(21)
                .time(70)
                .build())
                .standardTempGenerator();

        val expectedNumbers = List.of(
                21, 329, 425, 482, 524, 556, 583, 606,
                625, 643, 658, 673, 685, 697, 708, 719,
                728, 737, 746, 754, 761, 769, 776, 782,
                789, 795, 800, 806, 812, 817, 822, 827,
                831, 836, 840, 845, 849, 853, 857, 861,
                865, 868, 872, 876, 879, 882, 886, 889,
                892, 895, 898, 901, 904, 907, 910, 912,
                915, 918, 920, 923, 925, 928, 930, 933,
                935, 937, 940, 942, 944, 946, 948
        );

        assertEquals(expectedNumbers, generator.generate());
    }

    @Test
    public void generateOneNumberTest() {
        val generator = new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .t0(100)
                .time(0)
                .build())
                .standardTempGenerator();

        val expectedNumbers = List.of(100);

        assertEquals(expectedNumbers, generator.generate());
    }

    @Test(expected = ValidationException.class)
    public void invalidParametersTest() {
        new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .t0(-100)
                .time(-200)
                .build())
                .standardTempGenerator();
    }

}