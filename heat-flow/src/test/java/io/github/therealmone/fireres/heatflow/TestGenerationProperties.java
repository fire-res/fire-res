package io.github.therealmone.fireres.heatflow;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final int SENSOR_COUNT = 3;
    public static final double BOUND = 3.5000;

    public static final List<Point<Double>> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new HeatFlowPoint(10, 1.000));
        add(new HeatFlowPoint(50, 2.000));
        add(new HeatFlowPoint(70, 3.333));
    }};

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(HeatFlowProperties.builder()
                .sensorCount(SENSOR_COUNT)
                .bound(BOUND)
                .functionForm(FunctionForm.<Double>builder()
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .build())
                .build());

        setSamples(List.of(props));
    }
}
