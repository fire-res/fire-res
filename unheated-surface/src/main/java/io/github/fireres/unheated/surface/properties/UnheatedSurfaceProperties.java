package io.github.fireres.unheated.surface.properties;

import io.github.fireres.core.properties.BoundsShiftModifier;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.FunctionFormModifier;
import io.github.fireres.core.properties.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceProperties implements
        ReportProperties,
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<UnheatedSurfaceBoundsShift> {

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private Integer bound = 300;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private UnheatedSurfaceBoundsShift boundsShift = new UnheatedSurfaceBoundsShift();

    @Builder.Default
    private UnheatedSurfaceType type = UnheatedSurfaceType.PRIMARY;

}
