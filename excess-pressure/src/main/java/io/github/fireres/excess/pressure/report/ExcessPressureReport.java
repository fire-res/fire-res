package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.fireres.excess.pressure.model.Pressure;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ExcessPressureReport implements Report<ExcessPressureProperties> {

    private final UUID id;
    private final Sample sample;

    private Pressure pressure;
    private Double basePressure;
    private MinAllowedPressure minAllowedPressure;
    private MaxAllowedPressure maxAllowedPressure;

    @Override
    public ExcessPressureProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(ExcessPressureProperties.class)
                .orElseThrow();
    }
}
