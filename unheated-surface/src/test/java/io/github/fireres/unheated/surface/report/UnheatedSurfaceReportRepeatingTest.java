package io.github.fireres.unheated.surface.report;

import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.unheated.surface.UnheatedSurfaceTestUtils;
import io.github.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.fireres.core.test.TestUtils.repeatTest;

public class UnheatedSurfaceReportRepeatingTest extends AbstractTest {

    @Autowired
    private UnheatedSurfaceService unheatedSurfaceService;

    @Autowired
    private GenerationProperties generationProperties;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = unheatedSurfaceService.createReport(sample);

            UnheatedSurfaceTestUtils.assertUnheatedSurfaceReportIsValid(report);
        });
    }

}
