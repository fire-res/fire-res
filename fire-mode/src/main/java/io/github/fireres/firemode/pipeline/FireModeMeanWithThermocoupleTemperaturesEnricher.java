package io.github.fireres.firemode.pipeline;

import io.github.fireres.core.model.FunctionsGenerationBounds;
import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.firemode.generator.FireModeGenerationStrategy;
import io.github.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.fireres.firemode.model.ThermocoupleTemperature;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FireModeMeanWithThermocoupleTemperaturesEnricher implements ReportEnricher<FireModeReport> {

    private final FunctionsGenerationService functionsGenerationService;

    @Override
    public void enrich(FireModeReport report) {
        val lowerBound = new IntegerPointSequence(
                report.getMinAllowedTemperature()
                        .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedTemperatureShift()));

        val upperBound = new IntegerPointSequence(
                report.getMaxAllowedTemperature()
                        .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift()));

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanBounds(new FunctionsGenerationBounds(lowerBound, upperBound))
                        .childrenBounds(new FunctionsGenerationBounds(lowerBound, upperBound))
                        .childFunctionsCount(report.getProperties().getThermocoupleCount())
                        .strategy(new FireModeGenerationStrategy())
                        .build());

        report.setThermocoupleMeanTemperature(new ThermocoupleMeanTemperature(meanWithChildFunctions.getFirst().getValue()));

        report.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new ThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(FireModeReportEnrichType.MAINTAINED_TEMPERATURES);
    }
}
