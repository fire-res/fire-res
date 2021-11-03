package io.github.fireres.unheated.surface.report;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.TEST_ATTEMPTS;

public class UnheatedSurfaceReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private UnheatedSurfaceProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setUpBoundsShift() {
        sample.removeAllReports();
        reportProperties.getFunctionForm().getInterpolationPoints().clear();
        setupBoundsShift(reportProperties.getBoundsShift());
    }

    private void setupBoundsShift(UnheatedSurfaceBoundsShift primaryBoundsShift) {
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().clear();
        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().clear();

        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(10, -5));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(10, -5));

        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(50, 100));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(50, 100));
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        UnheatedSurfaceTestUtils.assertUnheatedSurfaceReportIsValid(report);
    }

}
