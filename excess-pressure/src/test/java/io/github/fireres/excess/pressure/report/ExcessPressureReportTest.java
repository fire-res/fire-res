package io.github.fireres.excess.pressure.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.excess.pressure.properties.ExcessPressureProperties;
import io.github.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.fireres.excess.pressure.config.TestConfig.PRESSURE_DELTA;

public class ExcessPressureReportTest extends AbstractTest {

    @Autowired
    private ExcessPressureService excessPressureService;

    @Autowired
    private ExcessPressureProperties reportProperties;

    @Autowired
    private Sample sample;

    @Before
    public void setup() {
        sample.removeAllReports();
    }

    @Test
    public void generateMinAllowedPressure() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val minAllowedPressure = report.getMinAllowedPressure();

        assertFunctionIsConstant(-PRESSURE_DELTA, minAllowedPressure.getValue());
    }

    @Test
    public void generateMaxAllowedPressure() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionIsConstant(PRESSURE_DELTA, maxAllowedPressure.getValue());
    }

    @Test
    public void generatePressure() {
        val report = excessPressureService.createReport(sample, reportProperties);

        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionNotHigher(report.getPressure().getValue(), maxAllowedPressure.getValue());
        assertFunctionNotLower(report.getPressure().getValue(), minAllowedPressure.getValue());
    }

}