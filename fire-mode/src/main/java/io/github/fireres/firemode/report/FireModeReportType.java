package io.github.fireres.firemode.report;

import io.github.fireres.core.model.ReportType;

public enum FireModeReportType implements ReportType {

    FIRE_MODE;

    @Override
    public String getDescription() {
        return "Режим пожара";
    }

}
