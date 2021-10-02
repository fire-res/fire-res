package io.github.fireres.core.service;

import io.github.fireres.core.model.Point;
import io.github.fireres.core.model.Report;

public interface InterpolationService<R extends Report, N extends Number> {

    void updateLinearityCoefficient(R report, Double linearityCoefficient);

    void updateDispersionCoefficient(R report, Double dispersionCoefficient);

    void updateChildFunctionsDeltaCoefficient(R report, Double childFunctionsDeltaCoefficient);

    void addInterpolationPoint(R report, Point<N> pointToAdd);

    void removeInterpolationPoint(R report, Point<N> pointToRemove);

}
