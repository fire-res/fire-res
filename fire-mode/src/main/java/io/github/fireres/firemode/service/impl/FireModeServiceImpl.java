package io.github.fireres.firemode.service.impl;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.fireres.core.service.impl.AbstractInterpolationService;
import io.github.fireres.firemode.model.FireModeType;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.report.FireModeReport;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MAINTAINED_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.STANDARD_TEMPERATURE;

@Service
public class FireModeServiceImpl extends AbstractInterpolationService<FireModeReport, Integer> implements FireModeService {

    private final ReportEnrichPipeline<FireModeReport> reportPipeline;

    @Autowired
    public FireModeServiceImpl(ReportEnrichPipeline<FireModeReport> reportPipeline) {
        super(report -> report.getProperties().getFunctionForm());
        this.reportPipeline = reportPipeline;
    }

    @Override
    public FireModeReport createReport(Sample sample, FireModeProperties properties) {
        val report = new FireModeReport(properties, sample);
        sample.addReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount) {
        report.getProperties().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateFireModeType(FireModeReport report, FireModeType fireModeType) {
        report.getProperties().setFireModeType(fireModeType);

        reportPipeline.accept(report, STANDARD_TEMPERATURE);
    }

    @Override
    public void updateTemperatureMaintaining(FireModeReport report, Integer temperatureMaintaining) {
        report.getProperties().setTemperaturesMaintaining(temperatureMaintaining);

        reportPipeline.accept(report, MAINTAINED_TEMPERATURES);
    }

    @Override
    public void updateShowBounds(FireModeReport report, Boolean showBounds) {
        report.getProperties().setShowBounds(showBounds);
    }

    @Override
    public void updateShowMeanTemperature(FireModeReport report, Boolean showMeanTemperature) {
        report.getProperties().setShowMeanTemperature(showMeanTemperature);
    }

    @Override
    protected void postUpdateLinearityCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public void addMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
