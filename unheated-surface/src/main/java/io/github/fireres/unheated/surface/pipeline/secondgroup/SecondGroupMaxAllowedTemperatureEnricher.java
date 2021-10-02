package io.github.fireres.unheated.surface.pipeline.secondgroup;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.unheated.surface.properties.SecondaryGroupProperties;
import io.github.fireres.core.pipeline.ReportEnrichType;
import io.github.fireres.unheated.surface.model.Group;
import io.github.fireres.unheated.surface.pipeline.SecondaryGroupMaxAllowedTemperatureEnricher;
import io.github.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MAX_ALLOWED_TEMPERATURE;

@Component
@Slf4j
public class SecondGroupMaxAllowedTemperatureEnricher extends SecondaryGroupMaxAllowedTemperatureEnricher {

    @Autowired
    public SecondGroupMaxAllowedTemperatureEnricher(GenerationProperties generationProperties) {
        super(generationProperties);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return SECOND_GROUP_MAX_ALLOWED_TEMPERATURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected Group getGroup(UnheatedSurfaceReport report) {
        return report.getSecondGroup();
    }

    @Override
    protected SecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report) {
        return report.getProperties().getSecondGroup();
    }
}
