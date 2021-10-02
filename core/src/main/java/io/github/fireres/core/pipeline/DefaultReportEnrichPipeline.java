package io.github.fireres.core.pipeline;

import io.github.fireres.core.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultReportEnrichPipeline<R extends Report> implements ReportEnrichPipeline<R> {

    private final List<ReportEnricher<R>> enrichers;

    @Override
    public void accept(R report) {
        log.info("Completely enriching report: {}, id: {} ", report.getClass().getSimpleName(), report.getId());

        enrichers.forEach(enricher -> enricher.enrich(report));
    }

    @Override
    public void accept(R report, ReportEnrichType enrichType) {
        log.info("Enriching report: {}, id: {} with: {}",
                report.getClass().getSimpleName(), report.getId(), ((Enum<?>) enrichType).name());

        val enricher = lookUpEnricher(enrichType);

        enricher.enrich(report);

        enricher.getAffectedTypes().forEach(affectedType -> {
            log.info("Enriching report: {}, id: {} with affected type: {}",
                    report.getClass().getSimpleName(), report.getId(), ((Enum<?>) affectedType).name());

            lookUpEnricher(affectedType).enrich(report);
        });
    }

    private ReportEnricher<R> lookUpEnricher(ReportEnrichType enrichType) {
        return enrichers.stream()
                .filter(enricher -> enricher.supports(enrichType))
                .findFirst()
                .orElseThrow();
    }
}
