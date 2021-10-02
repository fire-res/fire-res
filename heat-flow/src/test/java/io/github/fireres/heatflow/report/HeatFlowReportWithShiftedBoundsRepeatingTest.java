package io.github.fireres.heatflow.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.heatflow.TestGenerationProperties;
import io.github.fireres.heatflow.properties.HeatFlowProperties;
import io.github.fireres.heatflow.service.HeatFlowService;
import io.github.fireres.heatflow.model.HeatFlowPoint;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.repeatTest;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;

public class HeatFlowReportWithShiftedBoundsRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private HeatFlowService heatFlowService;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow();

        reportProperties.getFunctionForm().getInterpolationPoints().clear();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(10, -0.5));
        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(50, 1.0));
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = heatFlowService.createReport(sample);

            val bound = report.getBound()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedFlowShift());

            val mean = report.getMeanTemperature();

            assertFunctionConstantlyGrowing(mean.getValue());
            assertFunctionNotLower(mean.getValue(), constantFunction(TestGenerationProperties.TIME, 0).getValue());
            assertFunctionNotHigher(mean.getValue(), bound);

            val sensors = report.getSensorTemperatures();

            assertChildTemperaturesEqualsMean(sensors, mean);

            sensors.forEach(sensor -> {
                assertFunctionConstantlyGrowing(sensor.getValue());
                assertFunctionNotHigher(sensor.getValue(), bound);
                assertFunctionNotLower(sensor.getValue(), constantFunction(TestGenerationProperties.TIME, 0).getValue());
            });
        });
    }

}
