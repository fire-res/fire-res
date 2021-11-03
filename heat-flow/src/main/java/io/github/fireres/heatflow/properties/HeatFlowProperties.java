package io.github.fireres.heatflow.properties;

import io.github.fireres.core.properties.BoundsShiftModifier;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.FunctionFormModifier;
import io.github.fireres.core.properties.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowProperties implements
        FunctionFormModifier<Double>,
        BoundsShiftModifier<HeatFlowBoundsShift>,
        ReportProperties {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private Integer sensorCount = 3;

    @Builder.Default
    private Double bound = 3.5;

    @Builder.Default
    private FunctionForm<Double> functionForm = new FunctionForm<>();

    @Builder.Default
    private HeatFlowBoundsShift boundsShift = new HeatFlowBoundsShift();

}
