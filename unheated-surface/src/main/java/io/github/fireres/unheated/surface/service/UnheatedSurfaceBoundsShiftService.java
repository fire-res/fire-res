package io.github.fireres.unheated.surface.service;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.service.BoundsShiftService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void addMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

}
