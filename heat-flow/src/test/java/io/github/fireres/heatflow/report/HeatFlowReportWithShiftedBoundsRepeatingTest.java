package io.github.fireres.heatflow.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.service.HeatFlowService;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.TEST_ATTEMPTS;
import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.heatflow.config.TestConfig.TIME;

public class HeatFlowReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private HeatFlowService heatFlowService;

    @Autowired
    private HeatFlowProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setUpBoundsShift() {
        sample.removeAllReports();
        reportProperties.getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getBoundsShift().getMaxAllowedFlowShift().clear();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(10, -0.5));
        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(50, 1.0));
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {

        val report = heatFlowService.createReport(sample, reportProperties);

        val bound = report.getBound()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedFlowShift());

        val mean = report.getMeanTemperature();

        assertFunctionConstantlyGrowing(mean.getValue());
        assertFunctionNotLower(mean.getValue(), constantFunction(TIME, 0).getValue());
        assertFunctionNotHigher(mean.getValue(), bound);

        val sensors = report.getSensorTemperatures();

        assertChildTemperaturesEqualsMean(sensors, mean);

        sensors.forEach(sensor -> {
            assertFunctionConstantlyGrowing(sensor.getValue());
            assertFunctionNotHigher(sensor.getValue(), bound);
            assertFunctionNotLower(sensor.getValue(), constantFunction(TIME, 0).getValue());
        });
    }

}
