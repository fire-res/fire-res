package io.github.fireres.unheated.surface.properties;

import io.github.fireres.core.properties.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceProperties implements ReportProperties {

    @Builder.Default
    private PrimaryGroupProperties firstGroup = new PrimaryGroupProperties();

    @Builder.Default
    private SecondaryGroupProperties secondGroup = new SecondaryGroupProperties();

    @Builder.Default
    private SecondaryGroupProperties thirdGroup = new SecondaryGroupProperties();

}
