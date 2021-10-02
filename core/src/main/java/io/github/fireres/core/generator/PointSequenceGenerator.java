package io.github.fireres.core.generator;

import io.github.fireres.core.model.PointSequence;

public interface PointSequenceGenerator<T extends PointSequence<?>> {

    T generate();

}
