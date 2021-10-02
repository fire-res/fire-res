package io.github.fireres.heatflow.service;

import io.github.fireres.core.service.BoundsShiftService;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import io.github.fireres.heatflow.report.HeatFlowReport;

public interface HeatFlowBoundsShiftService extends BoundsShiftService {

    void addMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift);

    void removeMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift);

}
