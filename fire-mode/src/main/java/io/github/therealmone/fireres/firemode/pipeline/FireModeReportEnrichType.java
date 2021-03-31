package io.github.therealmone.fireres.firemode.pipeline;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;

public enum FireModeReportEnrichType implements ReportEnrichType {

    MIN_ALLOWED_TEMPERATURE,
    MAX_ALLOWED_TEMPERATURE,
    STANDARD_TEMPERATURE,
    FURNACE_TEMPERATURE,
    MEAN_WITH_THERMOCOUPLE_TEMPERATURES,
    MAINTAINED_TEMPERATURES

}
