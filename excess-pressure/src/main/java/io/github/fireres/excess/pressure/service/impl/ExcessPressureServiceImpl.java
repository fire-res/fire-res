package io.github.fireres.excess.pressure.service.impl;

import io.github.fireres.core.model.DoublePoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;
import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MAX_ALLOWED_PRESSURE;
import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MIN_ALLOWED_PRESSURE;
import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

@Service
@RequiredArgsConstructor
public class ExcessPressureServiceImpl implements ExcessPressureService {

    private final ReportEnrichPipeline<ExcessPressureReport> reportPipeline;

    @Override
    public ExcessPressureReport createReport(Sample sample, ExcessPressureProperties properties) {
        val report = new ExcessPressureReport(properties, sample);
        sample.addReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateBasePressure(ExcessPressureReport report, Double basePressure) {
        report.getProperties().setBasePressure(basePressure);

        reportPipeline.accept(report, BASE_PRESSURE);
    }

    @Override
    public void updateDelta(ExcessPressureReport report, Double delta) {
        report.getProperties().setDelta(delta);

        reportPipeline.accept(report, MIN_ALLOWED_PRESSURE);
        reportPipeline.accept(report, MAX_ALLOWED_PRESSURE);
    }

    @Override
    public void updateDispersionCoefficient(ExcessPressureReport report, Double dispersionCoefficient) {
        report.getProperties().setDispersionCoefficient(dispersionCoefficient);

        reportPipeline.accept(report, PRESSURE);
    }

    @Override
    public void addMaxAllowedPressureShift(ExcessPressureReport report, DoublePoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedPressureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, PRESSURE);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedPressureShift(ExcessPressureReport report, DoublePoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedPressureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, PRESSURE);
        }
    }

    @Override
    public void addMinAllowedPressureShift(ExcessPressureReport report, DoublePoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedPressureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, PRESSURE);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMinAllowedPressureShift(ExcessPressureReport report, DoublePoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedPressureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, PRESSURE);
        }
    }
}
