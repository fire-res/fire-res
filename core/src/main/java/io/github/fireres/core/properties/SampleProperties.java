package io.github.fireres.core.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private final UUID id = UUID.randomUUID();

    private String name;

}
