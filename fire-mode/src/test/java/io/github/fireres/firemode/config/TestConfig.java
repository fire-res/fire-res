package io.github.fireres.firemode.config;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.firemode.TestGenerationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class TestConfig {

    @Bean
    public GenerationProperties getGenerationProperties() {
        return new TestGenerationProperties();
    }

}
