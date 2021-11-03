package io.github.fireres.core.exception;

import io.github.fireres.core.model.ValidationError;

public class ParamsValidationException extends RuntimeException {

    public ParamsValidationException(ValidationError error) {
        super("Params validation error: " + error);
    }
}
