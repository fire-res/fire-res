package io.github.fireres.unheated.surface.pipeline.thirdgroup;

import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.pipeline.MeanWithThermocoupleTemperaturesEnricher;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Component
@Slf4j
public class ThirdGroupMeanWithThermocoupleTemperaturesEnricher extends MeanWithThermocoupleTemperaturesEnricher {

    @Autowired
    public ThirdGroupMeanWithThermocoupleTemperaturesEnricher(GenerationProperties generationProperties, FunctionsGenerationService functionsGenerationService) {
        super(generationProperties, functionsGenerationService);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

    @Override
    protected Group getGroup(UnheatedSurfaceReport report) {
        return report.getThirdGroup();
    }

    @Override
    protected FunctionForm<Integer> getFunctionForm(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getFunctionForm();
    }

    @Override
    protected BoundShift<IntegerPoint> getMeanBoundShift(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();
    }

    @Override
    protected BoundShift<IntegerPoint> getThermocoupleBoundShift(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getBoundsShift().getMaxAllowedTemperatureShift();
    }

    @Override
    protected Integer getThermocoupleCount(UnheatedSurfaceReport report) {
        return report.getProperties().getThirdGroup().getThermocoupleCount();
    }

}
