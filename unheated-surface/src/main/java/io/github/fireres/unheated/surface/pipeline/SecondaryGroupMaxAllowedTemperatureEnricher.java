package io.github.fireres.unheated.surface.pipeline;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.unheated.surface.properties.SecondaryGroupProperties;
import io.github.fireres.core.pipeline.ReportEnricher;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import io.github.fireres.unheated.surface.model.MaxAllowedThermocoupleTemperature;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static io.github.fireres.core.utils.FunctionUtils.constantFunction;

@RequiredArgsConstructor
public abstract class SecondaryGroupMaxAllowedTemperatureEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    private final GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val groupProperties = getGroupProperties(report);
        val time = generationProperties.getGeneral().getTime();

        val maxAllowedTemperature = constantFunction(time, groupProperties.getBound()).getValue();

        getGroup(report).setMaxAllowedThermocoupleTemperature(new MaxAllowedThermocoupleTemperature(maxAllowedTemperature));
        getGroup(report).setMaxAllowedMeanTemperature(new MaxAllowedMeanTemperature(maxAllowedTemperature));
    }

    protected abstract Group getGroup(UnheatedSurfaceReport report);

    protected abstract SecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report);

}
