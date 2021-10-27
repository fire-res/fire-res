package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;

public enum UnheatedSurfaceReportEnrichType implements ReportEnrichType {

    MAX_ALLOWED_MEAN_TEMPERATURE,
    MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE,
    MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

}
