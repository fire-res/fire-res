package io.github.fireres.unheated.surface.service;

import io.github.fireres.core.service.InterpolationService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceSecondGroupService extends
        InterpolationService<UnheatedSurfaceReport, Integer>,
        UnheatedSurfaceSecondaryBoundsShiftService {

    void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount);

    void updateBound(UnheatedSurfaceReport report, Integer bound);

    void refreshSecondGroup(UnheatedSurfaceReport report);

}
