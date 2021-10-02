package io.github.fireres.heatflow.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.heatflow.TestGenerationProperties;
import io.github.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.repeatTest;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;

public class HeatFlowReportRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private HeatFlowService heatFlowService;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = heatFlowService.createReport(sample);

            val bound = report.getBound();

            assertFunctionIsConstant(TestGenerationProperties.BOUND, bound.getValue());

            val mean = report.getMeanTemperature();

            assertFunctionConstantlyGrowing(mean.getValue());
            assertFunctionNotLower(mean.getValue(), constantFunction(TestGenerationProperties.TIME, 0).getValue());
            assertFunctionNotHigher(mean.getValue(), bound.getValue());

            val sensors = report.getSensorTemperatures();

            assertChildTemperaturesEqualsMean(sensors, mean);

            sensors.forEach(sensor -> {
                assertFunctionConstantlyGrowing(sensor.getValue());
                assertFunctionNotHigher(sensor.getValue(), bound.getValue());
                assertFunctionNotLower(sensor.getValue(), constantFunction(TestGenerationProperties.TIME, 0).getValue());
            });
        });
    }

}
