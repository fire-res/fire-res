package io.github.fireres.unheated.surface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    private final UUID id = UUID.randomUUID();

    private MaxAllowedMeanTemperature maxAllowedMeanTemperature;
    private MaxAllowedThermocoupleTemperature maxAllowedThermocoupleTemperature;
    private MeanTemperature meanTemperature;
    private List<ThermocoupleTemperature> thermocoupleTemperatures;

}
