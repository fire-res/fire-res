package io.github.fireres.heatflow.service;

import io.github.fireres.core.service.InterpolationService;
import io.github.fireres.core.service.ReportCreatorService;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.report.HeatFlowReport;

public interface HeatFlowService extends
        ReportCreatorService<HeatFlowReport, HeatFlowProperties>,
        InterpolationService<HeatFlowReport, Double>,
        HeatFlowBoundsShiftService {

    void updateSensorsCount(HeatFlowReport report, Integer sensorsCount);

    void updateBound(HeatFlowReport report, Double bound);

}
