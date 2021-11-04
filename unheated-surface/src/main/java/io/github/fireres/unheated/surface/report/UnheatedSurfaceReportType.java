package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.model.ReportType;

public enum UnheatedSurfaceReportType implements ReportType {

    UNHEATED_SURFACE;

    @Override
    public String getDescription() {
        return "Необогреваемая поверхность";
    }

}
