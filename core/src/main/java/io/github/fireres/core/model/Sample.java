package io.github.fireres.core.model;

import io.github.fireres.core.properties.SampleProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class Sample {

    @Getter
    private final SampleProperties sampleProperties;

    private final Map<Class<? extends Report>, Report> reports = new HashMap<>();

    public <T extends Report> Optional<T> getReportByClass(Class<T> reportClass) {
        //noinspection unchecked
        return Optional.ofNullable((T) reports.get(reportClass));
    }

    public void putReport(Report report) {
        reports.put(report.getClass(), report);
    }

    public void removeReport(Report report) {
        reports.remove(report.getClass());
    }

    public Collection<Report> getReports() {
        return reports.values();
    }

    public UUID getId() {
        return sampleProperties.getId();
    }

}
