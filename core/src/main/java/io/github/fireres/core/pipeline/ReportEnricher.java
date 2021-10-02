package io.github.fireres.core.pipeline;

import io.github.fireres.core.model.Report;

import java.util.Collections;
import java.util.List;

public interface ReportEnricher<R extends Report> {

    void enrich(R report);

    boolean supports(ReportEnrichType enrichType);

    default List<ReportEnrichType> getAffectedTypes() {
        return Collections.emptyList();
    }

}
