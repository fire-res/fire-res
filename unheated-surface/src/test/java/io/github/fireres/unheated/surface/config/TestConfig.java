package io.github.fireres.unheated.surface.config;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.unheated.surface.TestGenerationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class TestConfig {

    @Bean
    public GenerationProperties getGenerationProperties() {
        return new TestGenerationProperties();
    }

}
