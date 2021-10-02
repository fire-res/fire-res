package io.github.fireres.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public abstract class PointSequence<T extends Point<?>> {

    private final List<T> value;

    public T getPoint(Integer time) {
        return getValue().stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow();
    }

}
