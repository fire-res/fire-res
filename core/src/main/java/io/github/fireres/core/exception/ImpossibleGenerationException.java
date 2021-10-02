package io.github.fireres.core.exception;

public class ImpossibleGenerationException extends RuntimeException {
    public ImpossibleGenerationException() {
        super("Impossible to generate report with current configuration");
    }
}
