package io.github.fireres.firemode.utils;

import io.github.fireres.firemode.model.MaxAllowedTemperature;
import io.github.fireres.firemode.model.MinAllowedTemperature;
import io.github.fireres.firemode.model.StandardTemperature;
import io.github.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.fireres.firemode.model.ThermocoupleTemperature;
import io.github.fireres.firemode.report.FireModeReport;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.fireres.core.utils.FunctionUtils.merge;

@UtilityClass
public class FireModeUtils {

    public static MinAllowedTemperature getMaintainedMinAllowedTemperature(FireModeReport report) {
        if (report.getMaintainedTemperatures() != null) {
            return new MinAllowedTemperature(
                    merge(report.getMinAllowedTemperature(), report.getMaintainedTemperatures().getMinAllowedTemperature())
                            .getValue());
        } else {
            return report.getMinAllowedTemperature();
        }
    }

    public static StandardTemperature getMaintainedStandardTemperature(FireModeReport report) {
        if (report.getMaintainedTemperatures() != null) {
            return new StandardTemperature(
                    merge(report.getStandardTemperature(), report.getMaintainedTemperatures().getStandardTemperature())
                            .getValue());
        } else {
            return report.getStandardTemperature();
        }
    }

    public static MaxAllowedTemperature getMaintainedMaxAllowedTemperature(FireModeReport report) {
        if (report.getMaintainedTemperatures() != null) {
            return new MaxAllowedTemperature(
                    merge(report.getMaxAllowedTemperature(), report.getMaintainedTemperatures().getMaxAllowedTemperature())
                            .getValue());
        } else {
            return report.getMaxAllowedTemperature();
        }
    }

    public static List<ThermocoupleTemperature> getMaintainedThermocoupleTemperatures(FireModeReport report) {
        if (report.getMaintainedTemperatures() != null) {
            return IntStream.range(0, report.getThermocoupleTemperatures().size())
                    .mapToObj(i -> new ThermocoupleTemperature(
                            merge(
                                    report.getThermocoupleTemperatures().get(i),
                                    report.getMaintainedTemperatures().getThermocoupleTemperatures().get(i))
                            .getValue()))
                    .collect(Collectors.toList());
        } else {
            return report.getThermocoupleTemperatures();
        }
    }

    public static ThermocoupleMeanTemperature getMaintainedThermocoupleMeanTemperature(FireModeReport report) {
        if (report.getMaintainedTemperatures() != null) {
            return new ThermocoupleMeanTemperature(
                    merge(report.getThermocoupleMeanTemperature(), report.getMaintainedTemperatures().getThermocoupleMeanTemperature())
                            .getValue());
        } else {
            return report.getThermocoupleMeanTemperature();
        }
    }

}
