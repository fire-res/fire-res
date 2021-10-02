package io.github.fireres.heatflow.service.impl;

import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.heatflow.service.NormalizationService;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import io.github.fireres.heatflow.model.HeatFlowPointSequence;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NormalizationServiceImpl implements NormalizationService {

    public static final Integer NORMALIZATION_MULTIPLIER = 1000;

    @Override
    public IntegerPointSequence disnormalize(List<HeatFlowPoint> heatFlowPoints) {
        return new IntegerPointSequence(heatFlowPoints.stream()
                .map(p -> new IntegerPoint(p.getTime(), p.getIntValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public HeatFlowPointSequence normalize(IntegerPointSequence integerPointSequence) {
        return new HeatFlowPointSequence(integerPointSequence.getValue().stream()
                .map(p -> new HeatFlowPoint(p.getTime(), p.getValue() / 1000d))
                .collect(Collectors.toList()));
    }
}
