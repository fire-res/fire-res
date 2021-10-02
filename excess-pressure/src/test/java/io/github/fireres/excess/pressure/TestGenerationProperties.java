package io.github.fireres.excess.pressure;

import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import lombok.val;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;
    public static final double BASE_PRESSURE = 10.0;
    public static final double PRESSURE_DELTA = 2.0;
    public static final double DISPERSION_COEFFICIENT = 0.999;

    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(ExcessPressureProperties.builder()
                .basePressure(BASE_PRESSURE)
                .delta(PRESSURE_DELTA)
                .dispersionCoefficient(DISPERSION_COEFFICIENT)
                .build());

        setSamples(List.of(props));
    }
}
