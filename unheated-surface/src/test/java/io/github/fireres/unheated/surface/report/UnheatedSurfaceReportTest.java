package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.fireres.unheated.surface.TestGenerationProperties.ENVIRONMENT_TEMPERATURE;
import static io.github.fireres.unheated.surface.TestGenerationProperties.SECOND_GROUP_BOUND;
import static io.github.fireres.unheated.surface.TestGenerationProperties.THIRD_GROUP_BOUND;
import static io.github.fireres.unheated.surface.TestGenerationProperties.TIME;

public class UnheatedSurfaceReportTest extends AbstractTest {

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private GenerationProperties generationProperties;

    @Test
    public void generateMeanBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        //first group
        {
            val meanBound = report.getFirstGroup().getMaxAllowedMeanTemperature();

            assertFunctionIsConstant(
                    140 + ENVIRONMENT_TEMPERATURE,
                    meanBound.getValue());
        }

        //second group
        {
            val meanBound = report.getSecondGroup().getMaxAllowedMeanTemperature();
            assertFunctionIsConstant(SECOND_GROUP_BOUND, meanBound.getValue());
        }

        //third group
        {
            val meanBound = report.getThirdGroup().getMaxAllowedMeanTemperature();
            assertFunctionIsConstant(THIRD_GROUP_BOUND, meanBound.getValue());
        }
    }

    @Test
    public void generateThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        //first group
        {
            val thermocoupleBound = report.getFirstGroup().getMaxAllowedThermocoupleTemperature();

            assertFunctionIsConstant(
                    180 + ENVIRONMENT_TEMPERATURE,
                    thermocoupleBound.getValue());
        }

        //second group
        {
            val thermocoupleBound = report.getSecondGroup().getMaxAllowedThermocoupleTemperature();

            assertFunctionIsConstant(
                    SECOND_GROUP_BOUND,
                    thermocoupleBound.getValue());
        }

        //third group
        {
            val thermocoupleBound = report.getThirdGroup().getMaxAllowedThermocoupleTemperature();

            assertFunctionIsConstant(
                    THIRD_GROUP_BOUND,
                    thermocoupleBound.getValue());
        }

    }

    @Test
    public void generateFirstGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val firstGroup = report.getFirstGroup();

        val thermocoupleBound = firstGroup.getMaxAllowedThermocoupleTemperature();
        val meanBound = firstGroup.getMaxAllowedMeanTemperature();

        val meanTemperature = firstGroup.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = firstGroup.getThermocoupleTemperatures();

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
    public void generateSecondGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val secondGroup = report.getSecondGroup();

        val meanTemperature = secondGroup.getMeanTemperature();
        val thermocoupleBound = secondGroup.getMaxAllowedThermocoupleTemperature();

        assertFunctionIsConstant(
                SECOND_GROUP_BOUND,
                thermocoupleBound.getValue());
        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = secondGroup.getThermocoupleTemperatures();

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
    public void generateThirdGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val thirdGroup = report.getThirdGroup();

        val meanTemperature = thirdGroup.getMeanTemperature();
        val thermocoupleBound = thirdGroup.getMaxAllowedThermocoupleTemperature();

        assertFunctionIsConstant(
                THIRD_GROUP_BOUND,
                thermocoupleBound.getValue());
        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = thirdGroup.getThermocoupleTemperatures();

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