package io.github.fireres.unheated.surface.service.impl;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnheatedSurfaceServiceImpl implements UnheatedSurfaceService {

    private final ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Override
    public UnheatedSurfaceReport createReport(UUID reportId, Sample sample) {
        val report = new UnheatedSurfaceReport(reportId, sample);

        report.setFirstGroup(new Group());
        report.setSecondGroup(new Group());
        report.setThirdGroup(new Group());

        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

}
