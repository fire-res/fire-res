package io.github.fireres.heatflow.service;

import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import io.github.fireres.heatflow.model.HeatFlowPointSequence;

import java.util.List;

public interface NormalizationService {

    IntegerPointSequence disnormalize(List<HeatFlowPoint> heatFlowPoints);

    HeatFlowPointSequence normalize(IntegerPointSequence integerPointSequence);

}
