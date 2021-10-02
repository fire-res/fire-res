package io.github.fireres.firemode.model;

import lombok.Getter;

import java.util.function.Function;

import static java.lang.Math.log10;
import static java.lang.Math.round;
import static java.lang.Math.tanh;

public enum FireModeType {

    LOG(t -> (int) round(345 * log10(8 * t + 1))),
    TANH(t -> (int) round(480 * tanh(t / 8d)));

    @Getter
    private final Function<Integer, Integer> function;

    FireModeType(Function<Integer, Integer> function) {
        this.function = function;
    }
}
