package io.github.fireres.firemode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintainedTemperatures {

    private StandardTemperature standardTemperature;
    private MinAllowedTemperature minAllowedTemperature;
    private MaxAllowedTemperature maxAllowedTemperature;
    private FurnaceTemperature furnaceTemperature;
    private List<ThermocoupleTemperature> thermocoupleTemperatures;
    private ThermocoupleMeanTemperature thermocoupleMeanTemperature;

}
