package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FireModeMaxAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Override
    public void enrich(FireModeReport report) {
        val standardTemperature = report.getStandardTemperature();

        val maxAllowedTemperature = new MaxAllowedTempGenerator(standardTemperature)
                .generate();

        report.setMaxAllowedTemperature(maxAllowedTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES, FireModeReportEnrichType.MAINTAINED_TEMPERATURES);
    }
}
