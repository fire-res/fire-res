package io.github.fireres.unheated.surface.config;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@TestConfiguration
public class TestConfig {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final int THERMOCOUPLES_COUNT = 6;
    public static final int BOUND = 300;

    public static final UUID UNHEATED_SURFACE_ID = UUID.randomUUID();

    @Bean
    public GeneralProperties getGenerationProperties() {
        return GeneralProperties.builder()
                .time(TIME)
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .build();
    }

    @Bean
    public UnheatedSurfaceProperties unheatedSurfaceProperties() {
        return UnheatedSurfaceProperties.builder()
                .id(UNHEATED_SURFACE_ID)
                .thermocoupleCount(THERMOCOUPLES_COUNT)
                .bound(BOUND)
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
