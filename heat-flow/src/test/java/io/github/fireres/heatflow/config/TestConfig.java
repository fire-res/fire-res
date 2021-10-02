package io.github.fireres.heatflow.config;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.heatflow.TestGenerationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public GenerationProperties getGenerationProperties() {
        return new TestGenerationProperties();
    }

}
