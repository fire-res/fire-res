package io.github.fireres.core.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private final UUID id = UUID.randomUUID();

    private String name;

    private final Map<Class<? extends ReportProperties>, ReportProperties> propertiesMap = new HashMap<>();

    public <T extends ReportProperties> Optional<T> getReportPropertiesByClass(Class<T> propertiesClass) {
        //noinspection unchecked
        return Optional.of((T) propertiesMap.get(propertiesClass));
    }

    public void putReportProperties(ReportProperties properties) {
        propertiesMap.put(properties.getClass(), properties);
    }

    public void removeReport(ReportProperties properties) {
        propertiesMap.remove(properties.getClass());
    }

    public Collection<ReportProperties> getAllProperties() {
        return propertiesMap.values();
    }

}
