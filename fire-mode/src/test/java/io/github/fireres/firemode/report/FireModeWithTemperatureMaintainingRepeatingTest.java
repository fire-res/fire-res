package io.github.fireres.firemode.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.core.test.TestUtils;
import io.github.fireres.firemode.TestGenerationProperties;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.core.test.TestUtils.repeatTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FireModeWithTemperatureMaintainingRepeatingTest extends AbstractTest {

    @Autowired
    private GenerationProperties generationProperties;

    @Autowired
    private FireModeService fireModeService;

    @Before
    public void setUpTemperatureMaintaining() {
        val sampleProperties = generationProperties.getSamples().get(0);

        val reportProperties = sampleProperties
                .getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow();

        reportProperties.getFunctionForm().getInterpolationPoints().clear();

        reportProperties.setTemperaturesMaintaining(600);
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = fireModeService.createReport(sample);

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

            assertEquals(6, thermocouplesTemps.size());
            assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                Assert.assertEquals(TestGenerationProperties.TIME, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
            });

            assertNotNull(report.getMaintainedTemperatures());
            assertNotNull(report.getMaintainedTemperatures().getFurnaceTemperature());
            assertNotNull(report.getMaintainedTemperatures().getStandardTemperature());
            assertNotNull(report.getMaintainedTemperatures().getMinAllowedTemperature());
            assertNotNull(report.getMaintainedTemperatures().getMaxAllowedTemperature());
            assertNotNull(report.getMaintainedTemperatures().getThermocoupleTemperatures());
            assertNotNull(report.getMaintainedTemperatures().getThermocoupleMeanTemperature());

            TestUtils.assertSizesEquals(65, report.getMaintainedTemperatures().getFurnaceTemperature().getValue());
            TestUtils.assertSizesEquals(65, report.getMaintainedTemperatures().getStandardTemperature().getValue());
            TestUtils.assertSizesEquals(65, report.getMaintainedTemperatures().getMinAllowedTemperature().getValue());
            TestUtils.assertSizesEquals(65, report.getMaintainedTemperatures().getMaxAllowedTemperature().getValue());
            TestUtils.assertSizesEquals(65, report.getMaintainedTemperatures().getThermocoupleMeanTemperature().getValue());

            Assert.assertEquals(6, report.getMaintainedTemperatures().getThermocoupleTemperatures().size());

            report.getMaintainedTemperatures().getThermocoupleTemperatures()
                    .forEach(thermocoupleTemperature ->
                            TestUtils.assertSizesEquals(65, thermocoupleTemperature.getValue()));
        });
    }

}
