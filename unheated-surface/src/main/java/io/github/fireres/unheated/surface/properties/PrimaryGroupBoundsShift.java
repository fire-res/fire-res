package io.github.fireres.unheated.surface.properties;

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
public class PrimaryGroupBoundsShift implements BoundsShift {

    @Builder.Default
    private BoundShift<IntegerPoint> maxAllowedMeanTemperatureShift = new BoundShift<>();

    @Builder.Default
    private BoundShift<IntegerPoint> maxAllowedThermocoupleTemperatureShift = new BoundShift<>();

}
