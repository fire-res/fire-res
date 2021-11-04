package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.ReportType;
import io.github.fireres.core.model.Sample;
import io.github.fireres.excess.pressure.model.ExcessPressureMaxAllowedPressure;
import io.github.fireres.excess.pressure.model.ExcessPressureMinAllowedPressure;
import io.github.fireres.excess.pressure.model.ExcessPressure;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static io.github.fireres.excess.pressure.report.ExcessPressureReportType.EXCESS_PRESSURE;

@Data
@RequiredArgsConstructor
public class ExcessPressureReport implements Report<ExcessPressureProperties> {

    private final ReportType type = EXCESS_PRESSURE;

    private final ExcessPressureProperties properties;
    private final Sample sample;

    private ExcessPressure pressure;
    private Double basePressure;
    private ExcessPressureMinAllowedPressure minAllowedPressure;
    private ExcessPressureMaxAllowedPressure maxAllowedPressure;

    @Override
    public UUID getId() {
        return properties.getId();
    }
}
