package io.github.fireres.core.service;

import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.ValidationResult;

public interface ValidationService {

    ValidationResult validate(FunctionsGenerationParams params);

}
