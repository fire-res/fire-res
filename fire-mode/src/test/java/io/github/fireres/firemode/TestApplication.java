package io.github.fireres.firemode;

import io.github.fireres.firemode.config.TestConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(TestConfig.class)
@ComponentScan(basePackages = "io.github.fireres")
public class TestApplication {
}
