package io.github.fireres.unheated.surface.service.impl;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.service.impl.AbstractInterpolationService;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Service
public class UnheatedSurfaceServiceImpl extends AbstractInterpolationService<UnheatedSurfaceReport, Integer> implements UnheatedSurfaceService {

    private final ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Autowired
    public UnheatedSurfaceServiceImpl(ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline) {
        super(report -> report.getProperties().getFunctionForm());
        this.reportPipeline = reportPipeline;
    }

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getProperties().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateBound(UnheatedSurfaceReport report, Integer bound) {
        report.getProperties().setBound(bound);

        reportPipeline.accept(report, MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE);
    }

    @Override
    protected void postUpdateLinearityCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedMeanTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedMeanTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public void addMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public UnheatedSurfaceReport createReport(UUID reportId, Sample sample) {
        val report = new UnheatedSurfaceReport(reportId, sample);

        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

}
