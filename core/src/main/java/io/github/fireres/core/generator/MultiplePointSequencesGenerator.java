package io.github.fireres.core.generator;

import io.github.fireres.core.model.PointSequence;

import java.util.List;

public interface MultiplePointSequencesGenerator<T extends PointSequence<?>> {

    List<T> generate();

}
