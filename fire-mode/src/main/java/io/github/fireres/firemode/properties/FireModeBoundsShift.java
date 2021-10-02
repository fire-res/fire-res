package io.github.fireres.firemode.properties;

import io.github.fireres.core.properties.BoundsShift;
import io.github.fireres.core.model.BoundShift;
import io.github.fireres.core.model.IntegerPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireModeBoundsShift implements BoundsShift {

    @Builder.Default
    BoundShift<IntegerPoint> maxAllowedTemperatureShift = new BoundShift<>();

    @Builder.Default
    BoundShift<IntegerPoint> minAllowedTemperatureShift = new BoundShift<>();

}
