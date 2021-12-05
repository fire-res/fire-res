package io.github.fireres.firemode.config;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.firemode.properties.FireModeProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TestConfiguration
public class TestConfig {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final double LINEARITY_COEFFICIENT = 0.3;

    public static final UUID FIRE_MODE_ID = UUID.randomUUID();

    public static final List<Point<Integer>> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new IntegerPoint(0, 40));
        add(new IntegerPoint(1, 306));
        add(new IntegerPoint(18, 749));
        add(new IntegerPoint(21, 789));
        add(new IntegerPoint(26, 822));
        add(new IntegerPoint(48, 898));
        add(new IntegerPoint(49, 901));
        add(new IntegerPoint(70, 943));
    }};

    @Bean
    public GeneralProperties generalProperties() {
        return GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build();
    }

    @Bean
    public FireModeProperties fireModeProperties() {
        return FireModeProperties.builder()
                .id(FIRE_MODE_ID)
                .functionForm(FunctionForm.<Integer>builder()
                        .linearityCoefficient(LINEARITY_COEFFICIENT)
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .build())
                .thermocoupleCount(6)
                .build();
    }

    @Bean
    public SampleProperties sampleProperties() {
        return SampleProperties.builder()
                .name("test sample")
                .build();
    }

    @Bean
    public Sample sample(SampleProperties sampleProperties) {
        return new Sample(sampleProperties);
    }

}
