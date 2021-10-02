package io.github.fireres.unheated.surface.service.impl;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.service.impl.AbstractInterpolationService;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MAX_ALLOWED_TEMPERATURE;

@Service
public class UnheatedSurfaceThirdGroupServiceImpl extends AbstractInterpolationService<UnheatedSurfaceReport, Integer>
        implements UnheatedSurfaceThirdGroupService {

    private final ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Autowired
    public UnheatedSurfaceThirdGroupServiceImpl(ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline) {
        super(report -> report.getProperties().getThirdGroup().getFunctionForm());
        this.reportPipeline = reportPipeline;
    }

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getProperties().getThirdGroup().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateBound(UnheatedSurfaceReport report, Integer bound) {
        report.getProperties().getThirdGroup().setBound(bound);

        reportPipeline.accept(report, THIRD_GROUP_MAX_ALLOWED_TEMPERATURE);
    }

    @Override
    public void refreshThirdGroup(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MAX_ALLOWED_TEMPERATURE);
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateLinearityCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
