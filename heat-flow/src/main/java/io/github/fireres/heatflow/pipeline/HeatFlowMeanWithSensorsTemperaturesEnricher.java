package io.github.fireres.heatflow.pipeline;

import io.github.fireres.core.model.FunctionsGenerationBounds;
import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.core.properties.GeneralProperties;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.heatflow.service.NormalizationService;
import io.github.fireres.heatflow.generator.HeatFlowGenerationStrategy;
import io.github.fireres.heatflow.model.MeanTemperature;
import io.github.fireres.heatflow.model.SensorTemperature;
import io.github.fireres.heatflow.report.HeatFlowReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;

@Component
@Slf4j
@RequiredArgsConstructor
public class HeatFlowMeanWithSensorsTemperaturesEnricher implements ReportEnricher<HeatFlowReport> {

    private final GeneralProperties generalProperties;
    private final FunctionsGenerationService functionsGenerationService;
    private final NormalizationService normalizationService;

    @Override
    public void enrich(HeatFlowReport report) {
        val time = generalProperties.getTime();
        val bound = normalizationService.disnormalize(report.getBound()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedFlowShift()));
        val zeroBound = constantFunction(time, 0);

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanBounds(new FunctionsGenerationBounds(zeroBound, bound))
                        .childrenBounds(new FunctionsGenerationBounds(zeroBound, bound))
                        .childFunctionsCount(report.getProperties().getSensorCount())
                        .strategy(new HeatFlowGenerationStrategy())
                        .build());

        report.setMeanTemperature(new MeanTemperature(
                normalizationService.normalize(meanWithChildFunctions.getFirst()).getValue()));

        report.setSensorTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new SensorTemperature(
                        normalizationService.normalize(child).getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MEAN_WITH_SENSORS_TEMPERATURES.equals(enrichType);
    }
}
