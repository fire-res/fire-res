package io.github.fireres.excess.pressure.service;

import io.github.fireres.core.service.ReportCreatorService;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;

public interface ExcessPressureService extends
        ReportCreatorService<ExcessPressureReport, ExcessPressureProperties>,
        ExcessPressureBoundsShiftService {

    void updateBasePressure(ExcessPressureReport report, Double basePressure);

    void updateDelta(ExcessPressureReport report, Double delta);

    void updateDispersionCoefficient(ExcessPressureReport report, Double dispersionCoefficient);

}
