package io.github.fireres.firemode.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.FireModeTestUtils;
import io.github.fireres.firemode.TestGenerationProperties;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.repeatTest;

public class FireModeReportRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private FireModeService fireModeService;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = fireModeService.createReport(sample);

            FireModeTestUtils.chooseNextFireModeType(report.getProperties());

            val furnaceTemp = report.getFurnaceTemperature().getValue();
            val minAllowedTemp = report.getMinAllowedTemperature().getValue();
            val maxAllowedTemp = report.getMaxAllowedTemperature().getValue();
            val standardTemp = report.getStandardTemperature().getValue();

            //noinspection unchecked
            assertSizesEquals(TestGenerationProperties.TIME, furnaceTemp, minAllowedTemp, maxAllowedTemp, standardTemp);

            assertFunctionConstantlyGrowing(minAllowedTemp);
            assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);

            assertFunctionNotLower(standardTemp, minAllowedTemp);
            assertFunctionNotHigher(standardTemp, maxAllowedTemp);

            val meanTemp = report.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);

            val thermocouplesTemps = report.getThermocoupleTemperatures();

            Assert.assertEquals(6, thermocouplesTemps.size());
            assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                Assert.assertEquals(TestGenerationProperties.TIME, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
            });
        });
    }

}
