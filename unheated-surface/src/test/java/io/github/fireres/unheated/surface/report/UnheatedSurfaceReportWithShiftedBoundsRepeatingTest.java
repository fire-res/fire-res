package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.UnheatedSurfaceTestUtils;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceBoundsShift;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.repeatTest;

public class UnheatedSurfaceReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private GenerationProperties generationProperties;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow();

        reportProperties.getFunctionForm().getInterpolationPoints().clear();

        setupBoundsShift(reportProperties.getBoundsShift());
    }

    private void setupBoundsShift(UnheatedSurfaceBoundsShift primaryBoundsShift) {
        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(10, -5));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(10, -5));

        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(50, 100));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(50, 100));
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = unheatedSurfaceService.createReport(sample);

            UnheatedSurfaceTestUtils.assertUnheatedSurfaceReportIsValid(report);
        });
    }

}
