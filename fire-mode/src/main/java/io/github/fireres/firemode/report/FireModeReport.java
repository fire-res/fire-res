package io.github.fireres.firemode.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.model.FurnaceTemperature;
import io.github.fireres.firemode.model.MaintainedTemperatures;
import io.github.fireres.firemode.model.MaxAllowedTemperature;
import io.github.fireres.firemode.model.MinAllowedTemperature;
import io.github.fireres.firemode.model.StandardTemperature;
import io.github.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.fireres.firemode.model.ThermocoupleTemperature;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FireModeReport implements Report<FireModeProperties> {

    private final UUID id;
    private final Sample sample;

    private StandardTemperature standardTemperature;
    private MinAllowedTemperature minAllowedTemperature;
    private MaxAllowedTemperature maxAllowedTemperature;
    private FurnaceTemperature furnaceTemperature;
    private List<ThermocoupleTemperature> thermocoupleTemperatures;
    private ThermocoupleMeanTemperature thermocoupleMeanTemperature;

    private MaintainedTemperatures maintainedTemperatures;

    @Override
    public FireModeProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow();
    }
}
