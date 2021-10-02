package io.github.fireres.excess.pressure.config;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.excess.pressure.TestGenerationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@TestConfiguration
public class TestConfig {

    @Bean
    public GenerationProperties getGenerationProperties() {
        return new TestGenerationProperties();
    }

}
