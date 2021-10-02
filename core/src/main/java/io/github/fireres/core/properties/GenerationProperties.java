package io.github.fireres.core.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationProperties {

    @Builder.Default
    private GeneralProperties general = new GeneralProperties();

    @Builder.Default
    private List<SampleProperties> samples = new ArrayList<>();

    public SampleProperties getSampleById(UUID id) {
        return samples.stream()
                .filter(sample -> sample.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
