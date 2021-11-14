package io.github.fireres.core.properties;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "_class")
public interface ReportProperties {

    UUID getId();

    void setId(UUID id);

}
