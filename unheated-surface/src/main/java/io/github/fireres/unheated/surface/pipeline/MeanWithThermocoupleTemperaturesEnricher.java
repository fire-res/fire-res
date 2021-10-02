package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.FunctionsGenerationBounds;
import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.unheated.surface.generator.UnheatedSurfaceGenerationStrategy;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.model.MeanTemperature;
import io.github.fireres.unheated.surface.model.ThermocoupleTemperature;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;

@RequiredArgsConstructor
public abstract class MeanWithThermocoupleTemperaturesEnricher
        implements ReportEnricher<UnheatedSurfaceReport> {

    private final GenerationProperties generationProperties;
    private final FunctionsGenerationService functionsGenerationService;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val group = getGroup(report);

        val thermocoupleBound = new IntegerPointSequence(group.getMaxAllowedThermocoupleTemperature()
                .getShiftedValue(getThermocoupleBoundShift(report)));

        val meanBound = new IntegerPointSequence(group.getMaxAllowedMeanTemperature()
                .getShiftedValue(getMeanBoundShift(report)));

        val zeroBound = constantFunction(time, 0);

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(getFunctionForm(report))
                        .meanBounds(new FunctionsGenerationBounds(zeroBound, meanBound))
                        .childrenBounds(new FunctionsGenerationBounds(zeroBound, thermocoupleBound))
                        .childFunctionsCount(getThermocoupleCount(report))
                        .strategy(new UnheatedSurfaceGenerationStrategy())
                        .build());

        group.setMeanTemperature(new MeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        group.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new ThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    protected abstract Group getGroup(UnheatedSurfaceReport report);

    protected abstract FunctionForm<Integer> getFunctionForm(UnheatedSurfaceReport report);

    protected abstract BoundShift<IntegerPoint> getMeanBoundShift(UnheatedSurfaceReport report);

    protected abstract BoundShift<IntegerPoint> getThermocoupleBoundShift(UnheatedSurfaceReport report);

    protected abstract Integer getThermocoupleCount(UnheatedSurfaceReport report);

}
