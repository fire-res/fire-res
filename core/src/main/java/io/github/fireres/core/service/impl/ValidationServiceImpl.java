package io.github.fireres.core.service.impl;

import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.service.ValidationService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isFunctionGenerationParamsValid(FunctionsGenerationParams params) {
        return isUpperBoundGreaterThanLower(params)
                && isMeanFormInBounds(params)
                && isFormsConstantlyGrowing(params);
    }

    private boolean isMeanFormInBounds(FunctionsGenerationParams params) {
        val lowerBound = params.getMeanBounds().getLowerBound();
        val upperBound = params.getMeanBounds().getUpperBound();
        val meanForm = params.getMeanFunctionForm();

        for (Point<?> interpolationPoint : meanForm.getInterpolationPoints()) {
            val min = lowerBound.getPoint(interpolationPoint.getTime()).getValue();
            val max = upperBound.getPoint(interpolationPoint.getTime()).getValue();
            val pointValue = interpolationPoint.getIntValue();

            if (pointValue < min || pointValue > max) {
                return false;
            }
        }

        return true;
    }

    private boolean isFormsConstantlyGrowing(FunctionsGenerationParams params) {
        return isPointsConstantlyGrowing(params.getMeanFunctionForm().getInterpolationPoints());
    }

    private boolean isPointsConstantlyGrowing(List<? extends Point<?>> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            val point = points.get(i);
            val nextPoint = points.get(i + 1);

            if (point.getValue().doubleValue() > nextPoint.getValue().doubleValue()) {
                return false;
            }
        }

        return true;
    }

    private boolean isUpperBoundGreaterThanLower(FunctionsGenerationParams params) {
        val upperBound = params.getMeanBounds().getUpperBound();
        val lowerBound = params.getMeanBounds().getLowerBound();

        for (int i = 0; i < lowerBound.getValue().size(); i++) {
            val upperPoint = upperBound.getPoint(i);
            val lowerPoint = lowerBound.getPoint(i);

            if (upperPoint.getValue() < lowerPoint.getValue()) {
                return false;
            }
        }

        return true;
    }

}
