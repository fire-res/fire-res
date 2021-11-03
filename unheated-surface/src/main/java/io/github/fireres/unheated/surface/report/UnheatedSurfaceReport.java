package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import io.github.fireres.unheated.surface.model.MaxAllowedThermocoupleTemperature;
import io.github.fireres.unheated.surface.model.MeanTemperature;
import io.github.fireres.unheated.surface.model.ThermocoupleTemperature;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UnheatedSurfaceReport implements Report<UnheatedSurfaceProperties> {

    private final UnheatedSurfaceProperties properties;
    private final Sample sample;

    private MaxAllowedMeanTemperature maxAllowedMeanTemperature;
    private MaxAllowedThermocoupleTemperature maxAllowedThermocoupleTemperature;
    private MeanTemperature meanTemperature;
    private List<ThermocoupleTemperature> thermocoupleTemperatures;

    @Override
    public UUID getId() {
        return properties.getId();
    }
}
