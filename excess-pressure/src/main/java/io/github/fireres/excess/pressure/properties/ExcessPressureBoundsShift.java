package io.github.fireres.excess.pressure.properties;

import io.github.fireres.core.properties.BoundsShift;
import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.DoublePoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcessPressureBoundsShift implements BoundsShift {

    @Builder.Default
    private BoundShift<DoublePoint> maxAllowedPressureShift = new BoundShift<>();

    @Builder.Default
    private BoundShift<DoublePoint> minAllowedPressureShift = new BoundShift<>();

}
