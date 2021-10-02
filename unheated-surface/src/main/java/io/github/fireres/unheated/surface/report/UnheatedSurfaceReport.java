package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.model.Report;
import io.github.fireres.core.model.Sample;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import io.github.fireres.unheated.surface.model.Group;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UnheatedSurfaceReport implements Report<UnheatedSurfaceProperties> {

    private final UUID id;
    private final Sample sample;

    private Group firstGroup;
    private Group secondGroup;
    private Group thirdGroup;

    @Override
    public UnheatedSurfaceProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow();
    }
}
