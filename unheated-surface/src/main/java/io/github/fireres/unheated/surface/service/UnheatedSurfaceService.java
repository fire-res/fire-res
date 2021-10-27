package io.github.fireres.unheated.surface.service;

import io.github.fireres.core.service.InterpolationService;
import io.github.fireres.core.service.ReportCreatorService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceService extends
        ReportCreatorService<UnheatedSurfaceReport>,
        InterpolationService<UnheatedSurfaceReport, Integer>,
        UnheatedSurfaceBoundsShiftService {

    void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount);

    void updateBound(UnheatedSurfaceReport report, Integer bound);

}
