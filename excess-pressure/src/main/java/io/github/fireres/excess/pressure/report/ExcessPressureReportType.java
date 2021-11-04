package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.ReportType;

public enum ExcessPressureReportType implements ReportType {

    EXCESS_PRESSURE;

    @Override
    public String getDescription() {
        return "Избыточное давление";
    }

}
