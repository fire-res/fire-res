package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.IntegerPoint;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.UnheatedSurfaceTestUtils;
import io.github.fireres.unheated.surface.properties.PrimaryGroupBoundsShift;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import io.github.fireres.unheated.surface.properties.SecondaryGroupBoundsShift;
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

        reportProperties.getFirstGroup().getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getSecondGroup().getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getThirdGroup().getFunctionForm().getInterpolationPoints().clear();

        setUpPrimaryBoundsShift(reportProperties.getFirstGroup().getBoundsShift());
        setUpSecondaryBoundsShift(reportProperties.getSecondGroup().getBoundsShift());
        setUpSecondaryBoundsShift(reportProperties.getThirdGroup().getBoundsShift());
    }

    private void setUpPrimaryBoundsShift(PrimaryGroupBoundsShift primaryBoundsShift) {
        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(10, -5));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(10, -5));

        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(50, 100));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(50, 100));
    }

    private void setUpSecondaryBoundsShift(SecondaryGroupBoundsShift secondaryBoundsShift) {
        secondaryBoundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(10, -5));
        secondaryBoundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(50, 100));
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
