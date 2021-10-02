package io.github.fireres.core.service.impl;

import io.github.fireres.core.model.Point;
import io.github.fireres.core.model.Report;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.service.InterpolationService;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Comparator;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractInterpolationService<R extends Report<?>, N extends Number>
        implements InterpolationService<R, N> {

    private final Function<R, FunctionForm<N>> propertiesMapper;

    @Override
    public void updateLinearityCoefficient(R report, Double linearityCoefficient) {
        propertiesMapper.apply(report).setLinearityCoefficient(linearityCoefficient);

        postUpdateLinearityCoefficient(report);
    }

    @Override
    public void updateDispersionCoefficient(R report, Double dispersionCoefficient) {
        propertiesMapper.apply(report).setDispersionCoefficient(dispersionCoefficient);

        postUpdateDispersionCoefficient(report);
    }

    @Override
    public void updateChildFunctionsDeltaCoefficient(R report, Double childFunctionsDeltaCoefficient) {
        propertiesMapper.apply(report).setChildFunctionsDeltaCoefficient(childFunctionsDeltaCoefficient);

        postUpdateChildFunctionsDeltaCoefficient(report);
    }

    @Override
    public void addInterpolationPoint(R report, Point<N> pointToAdd) {
        val currentPoints = propertiesMapper.apply(report).getInterpolationPoints();

        if (currentPoints.stream().anyMatch(p -> p.getTime().equals(pointToAdd.getTime()))) {
            throw new IllegalArgumentException("Interpolation point already specified on time " + pointToAdd.getTime());
        }

        currentPoints.add(pointToAdd);
        currentPoints.sort(Comparator.comparing(Point::getTime));

        try {
            postUpdateInterpolationPoints(report);
        } catch (Exception e) {
            currentPoints.remove(pointToAdd);
            throw e;
        }
    }

    @Override
    public void removeInterpolationPoint(R report, Point<N> pointToRemove) {
        val currentPoints = propertiesMapper.apply(report).getInterpolationPoints();

        if (currentPoints.remove(pointToRemove)) {
            postUpdateInterpolationPoints(report);
        }
    }

    protected abstract void postUpdateLinearityCoefficient(R report);

    protected abstract void postUpdateDispersionCoefficient(R report);

    protected abstract void postUpdateInterpolationPoints(R report);

    protected abstract void postUpdateChildFunctionsDeltaCoefficient(R report);

}
