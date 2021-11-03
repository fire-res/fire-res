package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.model.FunctionsGenerationBounds;
import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.unheated.surface.generator.UnheatedSurfaceGenerationStrategy;
import io.github.fireres.unheated.surface.model.MeanTemperature;
import io.github.fireres.unheated.surface.model.ThermocoupleTemperature;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Component
@Slf4j
@RequiredArgsConstructor
public class UnheatedSurfaceMeanWithThermocoupleTemperaturesEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    private final GeneralProperties generalProperties;
    private final FunctionsGenerationService functionsGenerationService;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generalProperties.getTime();

        val thermocoupleBound = new IntegerPointSequence(report
                .getMaxAllowedThermocoupleTemperature()
                .getShiftedValue(report.getProperties().getBoundsShift()
                        .getMaxAllowedThermocoupleTemperatureShift()));

        val meanBound = new IntegerPointSequence(report
                .getMaxAllowedMeanTemperature()
                .getShiftedValue(report.getProperties().getBoundsShift()
                        .getMaxAllowedMeanTemperatureShift()));

        val zeroBound = constantFunction(time, 0);

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanBounds(new FunctionsGenerationBounds(zeroBound, meanBound))
                        .childrenBounds(new FunctionsGenerationBounds(zeroBound, thermocoupleBound))
                        .childFunctionsCount(report.getProperties().getThermocoupleCount())
                        .strategy(new UnheatedSurfaceGenerationStrategy())
                        .build());

        report.setMeanTemperature(new MeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        report.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new ThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }
}
