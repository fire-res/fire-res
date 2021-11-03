package io.github.fireres.core.model;

import io.github.fireres.core.properties.ReportProperties;
import io.github.fireres.core.properties.SampleProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Sample {

    @Getter
    private final SampleProperties sampleProperties;

    private final Queue<Report> reports = new ConcurrentLinkedDeque<>();

    public <T extends Report> Optional<T> getReportById(UUID reportId, Class<T> reportClass) {
        return reports.stream()
                .filter(report -> report.getId().equals(reportId))
                .map(reportClass::cast)
                .findFirst();
    }

    public void addReport(Report report) {
        if (reportWithIdExists(report.getId())) {
            throw new IllegalArgumentException("Report with id: " + report.getId() + " already exists");
        }

        reports.add(report);
    }

    private boolean reportWithIdExists(UUID reportId) {
        return reports.stream().anyMatch(report -> report.getId().equals(reportId));
    }

    public void removeReport(Report report) {
        if (report != null) {
            reports.removeIf(r -> r.getId().equals(report.getId()));
        }
    }

    public void removeAllReports() {
        reports.clear();
    }

    public Collection<Report> getReports() {
        return reports;
    }

    public UUID getId() {
        return sampleProperties.getId();
    }

    public List<ReportProperties> getReportProperties() {
        return reports.stream()
                .map(Report::getProperties)
                .collect(Collectors.toList());
    }

}
