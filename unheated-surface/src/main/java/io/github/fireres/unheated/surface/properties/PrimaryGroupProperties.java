package io.github.fireres.unheated.surface.properties;

import io.github.fireres.core.properties.BoundsShiftModifier;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.FunctionFormModifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PrimaryGroupProperties implements
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<PrimaryGroupBoundsShift> {

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private PrimaryGroupBoundsShift boundsShift = new PrimaryGroupBoundsShift();

}
