package io.github.fireres.heatflow.report;

import io.github.fireres.core.model.ReportType;

public enum HeatFlowReportType implements ReportType {

    HEAT_FLOW;

    @Override
    public String getDescription() {
        return "Тепловой поток";
    }

}
