package io.github.fireres.unheated.surface.service;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.service.BoundsShiftService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceSecondaryBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

    void removeMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift);

}
