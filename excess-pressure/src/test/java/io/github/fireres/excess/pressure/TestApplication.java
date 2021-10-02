package io.github.fireres.excess.pressure;

import io.github.fireres.excess.pressure.config.TestConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(TestConfig.class)
@ComponentScan(basePackages = "io.github.fireres")
public class TestApplication {
}
