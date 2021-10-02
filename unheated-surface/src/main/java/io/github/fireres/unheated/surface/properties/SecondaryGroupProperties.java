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
public class SecondaryGroupProperties implements
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<SecondaryGroupBoundsShift> {

    @Builder.Default
    private Integer bound = 300;

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private SecondaryGroupBoundsShift boundsShift = new SecondaryGroupBoundsShift();

}
