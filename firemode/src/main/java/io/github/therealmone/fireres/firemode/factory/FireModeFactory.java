package io.github.therealmone.fireres.firemode.factory;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.firemode.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.generator.MinAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.generator.StandardTempGenerator;
import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;

public class FireModeFactory {

    @Inject
    private GenerationProperties generationProperties;

    public StandardTemperature standardTemperature() {
        return new StandardTempGenerator(
                generationProperties.getGeneral().getEnvironmentTemperature(),
                generationProperties.getGeneral().getTime()
        ).generate();
    }

    public FurnaceTemperature furnaceTemperature(StandardTemperature standardTemp) {
        return new FurnaceTempGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getEnvironmentTemperature(),
                standardTemp
        ).generate();
    }

    public MinAllowedTemperature minAllowedTemperature(StandardTemperature standardTemp) {
        return new MinAllowedTempGenerator(
                generationProperties.getGeneral().getTime(),
                standardTemp
        ).generate();
    }

    public MaxAllowedTemperature maxAllowedTemperature(StandardTemperature standardTemp) {
        return new MaxAllowedTempGenerator(
                generationProperties.getGeneral().getTime(),
                standardTemp
        ).generate();
    }
}
