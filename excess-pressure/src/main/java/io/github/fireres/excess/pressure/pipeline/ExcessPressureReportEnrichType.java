package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;

public enum ExcessPressureReportEnrichType implements ReportEnrichType {

    BASE_PRESSURE,
    MIN_ALLOWED_PRESSURE,
    MAX_ALLOWED_PRESSURE,
    PRESSURE

}
