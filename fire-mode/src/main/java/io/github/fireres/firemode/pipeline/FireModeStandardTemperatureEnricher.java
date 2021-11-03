package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.firemode.generator.StandardTempGenerator;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.FURNACE_TEMPERATURE;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MAINTAINED_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MAX_ALLOWED_TEMPERATURE;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.MIN_ALLOWED_TEMPERATURE;
import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.STANDARD_TEMPERATURE;

@Slf4j
@Component
@RequiredArgsConstructor
public class FireModeStandardTemperatureEnricher implements ReportEnricher<FireModeReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(FireModeReport report) {
        val properties = report.getProperties();

        val standardTemperature = StandardTempGenerator.builder()
                .t0(generalProperties.getEnvironmentTemperature())
                .time(generalProperties.getTime())
                .fireModeType(properties.getFireModeType())
                .build()
                .generate();

        report.setStandardTemperature(standardTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return STANDARD_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(
                MIN_ALLOWED_TEMPERATURE,
                MAX_ALLOWED_TEMPERATURE,
                FURNACE_TEMPERATURE,
                MEAN_WITH_THERMOCOUPLE_TEMPERATURES,
                MAINTAINED_TEMPERATURES);
    }
}
