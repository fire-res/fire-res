package io.github.fireres.core.properties;

import io.github.fireres.core.model.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private Integer time;

    private Integer environmentTemperature;

    @Builder.Default
    private List<ReportType> includedReports = new ArrayList<>();

}
