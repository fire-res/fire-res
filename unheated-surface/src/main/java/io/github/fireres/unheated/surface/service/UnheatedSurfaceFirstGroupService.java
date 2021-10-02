package io.github.fireres.unheated.surface.service;

import io.github.fireres.core.service.InterpolationService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceFirstGroupService extends
        InterpolationService<UnheatedSurfaceReport, Integer>,
        UnheatedSurfacePrimaryBoundsShiftService {

    void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount);

    void refreshFirstGroup(UnheatedSurfaceReport report);

}
