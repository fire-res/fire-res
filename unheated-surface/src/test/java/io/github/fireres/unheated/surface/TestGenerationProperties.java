package io.github.fireres.unheated.surface;

import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.properties.SampleProperties;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import lombok.val;

import java.util.List;

public class TestGenerationProperties extends GenerationProperties {

    public static final int ENVIRONMENT_TEMPERATURE = 21;
    public static final int TIME = 71;

    public static final int THERMOCOUPLES_COUNT = 6;
    public static final int BOUND = 300;


    public TestGenerationProperties() {
        setGeneral(GeneralProperties.builder()
                .environmentTemperature(ENVIRONMENT_TEMPERATURE)
                .time(TIME)
                .build());

        val props = new SampleProperties();

        props.putReportProperties(UnheatedSurfaceProperties.builder()
                .thermocoupleCount(THERMOCOUPLES_COUNT)
                .bound(BOUND)
                .build());

        setSamples(List.of(props));
    }
}
