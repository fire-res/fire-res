package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.properties.UnheatedSurfaceProperties;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.config.TestConfig.BOUND;
import static io.github.fireres.unheated.surface.config.TestConfig.ENVIRONMENT_TEMPERATURE;
import static io.github.fireres.unheated.surface.config.TestConfig.TIME;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.PRIMARY;
import static io.github.fireres.unheated.surface.properties.UnheatedSurfaceType.SECONDARY;

public class UnheatedSurfaceReportTest extends AbstractTest {

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private UnheatedSurfaceProperties reportProperties;

    @Autowired
    private Sample sample;

    @Before
    public void setup() {
        sample.removeAllReports();
        reportProperties.setType(PRIMARY);
    }

    @Test
    public void generatePrimaryMeanBound() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val meanBound = report.getMaxAllowedMeanTemperature();

        assertFunctionIsConstant(
                140 + ENVIRONMENT_TEMPERATURE,
                meanBound.getValue());
    }

    @Test
    public void generateSecondaryMeanBound() {
        reportProperties.setType(SECONDARY);

        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val meanBound = report.getMaxAllowedMeanTemperature();

        assertFunctionIsConstant(BOUND, meanBound.getValue());
    }

    @Test
    public void generatePrimaryThermocoupleBound() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val thermocoupleBound = report.getMaxAllowedThermocoupleTemperature();

        assertFunctionIsConstant(
                180 + ENVIRONMENT_TEMPERATURE,
                thermocoupleBound.getValue());
    }

    @Test
    public void generateSecondaryThermocoupleBound() {
        reportProperties.setType(SECONDARY);

        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val thermocoupleBound = report.getMaxAllowedThermocoupleTemperature();

        assertFunctionIsConstant(BOUND, thermocoupleBound.getValue());
    }

    @Test
    public void generatePrimaryReport() {
        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val thermocoupleBound = report.getMaxAllowedThermocoupleTemperature();
        val meanBound = report.getMaxAllowedMeanTemperature();
        val meanTemperature = report.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = report.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(TIME, 0).getValue());
        }
    }

    @Test
    public void generateSecondaryReport() {
        reportProperties.setType(SECONDARY);

        val report = unheatedSurfaceService.createReport(sample, reportProperties);

        val meanTemperature = report.getMeanTemperature();
        val thermocoupleBound = report.getMaxAllowedThermocoupleTemperature();

        assertFunctionIsConstant(BOUND, thermocoupleBound.getValue());
        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = report.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(TIME, 0).getValue());
        }

    }

}