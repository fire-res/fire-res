package io.github.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Point<T extends Number> implements Cloneable {

    private Integer time;
    private T value;

    public abstract T getNormalizedValue();

    public Integer getIntValue() {
        return value.intValue();
    }

    @Override
    @SneakyThrows
    public Object clone() {
        return super.clone();
    }
}
