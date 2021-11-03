package io.github.fireres.heatflow.service.impl;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.service.impl.AbstractInterpolationService;
import io.github.fireres.heatflow.pipeline.HeatFlowReportEnrichType;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.report.HeatFlowReport;
import io.github.fireres.heatflow.service.HeatFlowService;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HeatFlowServiceImpl extends AbstractInterpolationService<HeatFlowReport, Double> implements HeatFlowService {

    private final ReportEnrichPipeline<HeatFlowReport> reportPipeline;

    @Autowired
    public HeatFlowServiceImpl(ReportEnrichPipeline<HeatFlowReport> reportPipeline) {
        super(report -> report.getProperties().getFunctionForm());
        this.reportPipeline = reportPipeline;
    }

    @Override
    public HeatFlowReport createReport(Sample sample, HeatFlowProperties properties) {
        val report = new HeatFlowReport(properties, sample);
        sample.addReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateSensorsCount(HeatFlowReport report, Integer sensorsCount) {
        report.getProperties().setSensorCount(sensorsCount);

        reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void updateBound(HeatFlowReport report, Double bound) {
        report.getProperties().setBound(bound);

        reportPipeline.accept(report, HeatFlowReportEnrichType.MAX_ALLOWED_FLOW);
    }

    @Override
    protected void postUpdateLinearityCoefficient(HeatFlowReport report) {
        reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(HeatFlowReport report) {
        reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(HeatFlowReport report) {
        reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(HeatFlowReport report) {
        reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedFlowShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedFlowShift(HeatFlowReport report, HeatFlowPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedFlowShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES);
        }
    }
}
