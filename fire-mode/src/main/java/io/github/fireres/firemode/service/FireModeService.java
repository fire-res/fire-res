package io.github.fireres.firemode.service;

import io.github.fireres.core.service.InterpolationService;
import io.github.fireres.core.service.ReportCreatorService;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.report.FireModeReport;
import io.github.fireres.firemode.model.FireModeType;

public interface FireModeService extends
        ReportCreatorService<FireModeReport, FireModeProperties>,
        InterpolationService<FireModeReport, Integer>,
        FireModeBoundsShiftService {

    void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount);

    void updateFireModeType(FireModeReport report, FireModeType fireModeType);

    void updateTemperatureMaintaining(FireModeReport report, Integer temperatureMaintaining);

    void updateShowBounds(FireModeReport report, Boolean showBounds);

    void updateShowMeanTemperature(FireModeReport report, Boolean showMeanTemperature);

}
