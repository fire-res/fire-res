package io.github.fireres.core.model;

import io.github.fireres.core.properties.ReportProperties;

import java.util.UUID;

public interface Report<P extends ReportProperties> {

    UUID getId();

    Sample getSample();

    P getProperties();

}
