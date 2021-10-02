package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.firemode.generator.MinAllowedTempGenerator;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MAINTAINED_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MIN_ALLOWED_TEMPERATURE;

@Slf4j
@Component
public class MinAllowedTemperatureEnricher implements ReportEnricher<FireModeReport> {

    @Override
    public void enrich(FireModeReport report) {
        val standardTemperature = report.getStandardTemperature();

        val minAllowedTemperature = new MinAllowedTempGenerator(standardTemperature)
                .generate();

        report.setMinAllowedTemperature(minAllowedTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MIN_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(MEAN_WITH_THERMOCOUPLE_TEMPERATURES, MAINTAINED_TEMPERATURES);
    }
}
