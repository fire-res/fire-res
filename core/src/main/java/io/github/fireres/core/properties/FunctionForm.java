package io.github.fireres.core.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.fireres.core.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionForm<N extends Number> {

    @Builder.Default
    private List<Point<N>> interpolationPoints = new ArrayList<>();

    @Builder.Default
    private Double linearityCoefficient = 1d;

    @Builder.Default
    private Double dispersionCoefficient = 0d;

    @Builder.Default
    private Double childFunctionsDeltaCoefficient = 0.25;

    public void setInterpolationPoints(List<Point<N>> interpolationPoints) {
        interpolationPoints.sort(Comparator.comparing(Point::getTime));
        this.interpolationPoints = interpolationPoints;
    }

    @JsonIgnore
    public Double getNonLinearityCoefficient() {
        return 1 - linearityCoefficient;
    }

}
