package io.github.fireres.firemode.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.service.FireModeService;
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
import static io.github.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.fireres.firemode.config.TestConfig.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FireModeWithTemperatureMaintainingRepeatingTest extends AbstractTest {

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private FireModeProperties reportProperties;

    @Autowired
    private Sample sample;

    @BeforeEach
    @Before
    public void setUpTemperatureMaintaining() {
        reportProperties.getFunctionForm().getInterpolationPoints().clear();
        reportProperties.setTemperaturesMaintaining(600);
    }

    @RepeatedTest(TEST_ATTEMPTS)
    @Test
    public void provideReportTest() {
        sample.removeAllReports();

        val report = fireModeService.createReport(sample, reportProperties);

        val furnaceTemp = report.getFurnaceTemperature().getValue();
        val minAllowedTemp = report.getMinAllowedTemperature().getValue();
        val maxAllowedTemp = report.getMaxAllowedTemperature().getValue();
        val standardTemp = report.getStandardTemperature().getValue();

        //noinspection unchecked
        assertSizesEquals(TIME, furnaceTemp, minAllowedTemp, maxAllowedTemp, standardTemp);

        assertFunctionConstantlyGrowing(minAllowedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);

        assertFunctionNotLower(furnaceTemp, minAllowedTemp);
        assertFunctionNotHigher(furnaceTemp, maxAllowedTemp);

        val meanTemp = report.getThermocoupleMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
        assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);

        val thermocouplesTemps = report.getThermocoupleTemperatures();

        assertEquals(6, thermocouplesTemps.size());
        assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

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

        assertSizesEquals(65, report.getMaintainedTemperatures().getFurnaceTemperature().getValue());
        assertSizesEquals(65, report.getMaintainedTemperatures().getStandardTemperature().getValue());
        assertSizesEquals(65, report.getMaintainedTemperatures().getMinAllowedTemperature().getValue());
        assertSizesEquals(65, report.getMaintainedTemperatures().getMaxAllowedTemperature().getValue());
        assertSizesEquals(65, report.getMaintainedTemperatures().getThermocoupleMeanTemperature().getValue());

        assertEquals(6, report.getMaintainedTemperatures().getThermocoupleTemperatures().size());

        report.getMaintainedTemperatures().getThermocoupleTemperatures()
                .forEach(thermocoupleTemperature ->
                        assertSizesEquals(65, thermocoupleTemperature.getValue()));
    }

}
