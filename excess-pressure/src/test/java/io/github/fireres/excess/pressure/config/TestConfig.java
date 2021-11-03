package io.github.fireres.excess.pressure.config;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@TestConfiguration
public class TestConfig {


    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final double BASE_PRESSURE = 10.0;
    public static final double PRESSURE_DELTA = 2.0;
    public static final double DISPERSION_COEFFICIENT = 0.999;

    public static final UUID EXCESS_PRESSURE_ID = UUID.randomUUID();

    @Bean
    public GeneralProperties generalProperties() {
        return GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build();
    }

    @Bean
    public ExcessPressureProperties excessPressureProperties() {
        return ExcessPressureProperties.builder()
                .id(EXCESS_PRESSURE_ID)
                .basePressure(BASE_PRESSURE)
                .delta(PRESSURE_DELTA)
                .dispersionCoefficient(DISPERSION_COEFFICIENT)
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
