package io.github.fireres.excess.pressure.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.excess.pressure.model.ExcessPressureMaxAllowedPressure;
import io.github.fireres.excess.pressure.model.ExcessPressureMinAllowedPressure;
import io.github.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.fireres.excess.pressure.generator.PressureGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import static io.github.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExcessPressureEnricher implements ReportEnricher<ExcessPressureReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generalProperties.getTime();

        val minAllowedPressure = new ExcessPressureMinAllowedPressure(report.getMinAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedPressureShift()));

        val maxAllowedPressure = new ExcessPressureMaxAllowedPressure(report.getMaxAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedPressureShift()));

        val dispersion = report.getProperties().getDispersionCoefficient();

        val pressure = new PressureGenerator(
                time, minAllowedPressure, maxAllowedPressure, dispersion)
                .generate();

        report.setPressure(pressure);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return PRESSURE.equals(enrichType);
    }

}
