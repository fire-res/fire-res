package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.firemode.generator.FurnaceTempGenerator;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import static io.github.fireres.firemode.pipeline.FireModeReportEnrichType.FURNACE_TEMPERATURE;

@Slf4j
@Component
@RequiredArgsConstructor
public class FireModeFurnaceTemperatureEnricher implements ReportEnricher<FireModeReport> {

    private final GeneralProperties generalProperties;

    @Override
    public void enrich(FireModeReport report) {
        val t0 = generalProperties.getEnvironmentTemperature();
        val standardTemperature = report.getStandardTemperature();

        val furnaceTemperature = new FurnaceTempGenerator(t0, standardTemperature)
                .generate();

        report.setFurnaceTemperature(furnaceTemperature);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FURNACE_TEMPERATURE.equals(enrichType);
    }

}
