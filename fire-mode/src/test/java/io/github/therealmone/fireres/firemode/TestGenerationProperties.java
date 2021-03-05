package io.github.therealmone.fireres.firemode;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final double LINEAR_COEFFICIENT = 0.3;

    public static final List<Point<Integer>> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new IntegerPoint(0, 21));
        add(new IntegerPoint(1, 306));
        add(new IntegerPoint(18, 749));
        add(new IntegerPoint(21, 789));
        add(new IntegerPoint(26, 822));
        add(new IntegerPoint(48, 898));
        add(new IntegerPoint(49, 901));
        add(new IntegerPoint(70, 943));
    }};

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(FireModeProperties.builder()
                .functionForm(FunctionForm.<Integer>builder()
                        .linearityCoefficient(LINEAR_COEFFICIENT)
                        .interpolationPoints(INTERPOLATION_POINTS)
                        .build())
                .thermocoupleCount(6)
                .build());

        setSamples(List.of(props));
    }
}
