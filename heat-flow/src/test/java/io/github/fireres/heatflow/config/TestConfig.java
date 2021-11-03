package io.github.fireres.heatflow.config;

import io.github.fireres.core.model.Point;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TestConfiguration
public class TestConfig {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final int SENSOR_COUNT = 3;
    public static final double BOUND = 3.5000;

    public static final UUID HEAT_FLOW_ID = UUID.randomUUID();

    public static final List<Point<Double>> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new HeatFlowPoint(10, 1.000));
        add(new HeatFlowPoint(50, 2.000));
        add(new HeatFlowPoint(70, 3.333));
    }};

    @Bean
    public GeneralProperties generalProperties() {
        return GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build();
    }

    @Bean
    public HeatFlowProperties heatFlowProperties() {
        return HeatFlowProperties.builder()
                .id(HEAT_FLOW_ID)
                .sensorCount(SENSOR_COUNT)
                .bound(BOUND)
                .functionForm(FunctionForm.<Double>builder()
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .build())
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
