package io.github.fireres.heatflow.properties;

import io.github.fireres.core.properties.BoundsShift;
import io.github.fireres.core.model.BoundShift;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowBoundsShift implements BoundsShift {

    @Builder.Default
    private BoundShift<HeatFlowPoint> maxAllowedFlowShift = new BoundShift<>();

}
