package io.github.fireres.core.service;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.properties.ReportProperties;

public interface ReportCreatorService<R extends Report<P>, P extends ReportProperties> {

    R createReport(Sample sample, P reportProperties);

}
