package io.github.fireres.firemode.properties;

import io.github.fireres.core.properties.BoundsShiftModifier;
import io.github.fireres.core.properties.FunctionFormModifier;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.ReportProperties;
import io.github.fireres.firemode.model.FireModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeProperties implements
        ReportProperties,
        FunctionFormModifier<Integer>,
        BoundsShiftModifier<FireModeBoundsShift> {

    @Builder.Default
    private Integer thermocoupleCount = 3;

    @Builder.Default
    private FunctionForm<Integer> functionForm = new FunctionForm<>();

    @Builder.Default
    private FireModeBoundsShift boundsShift = new FireModeBoundsShift();

    @Builder.Default
    private FireModeType fireModeType = FireModeType.LOG;

    @Builder.Default
    private Integer temperaturesMaintaining = 0;

    @Builder.Default
    private Boolean showBounds = true;

    @Builder.Default
    private Boolean showMeanTemperature = true;

}
