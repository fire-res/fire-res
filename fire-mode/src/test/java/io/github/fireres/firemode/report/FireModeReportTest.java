package io.github.fireres.firemode.report;

import io.github.fireres.core.model.Sample;
import io.github.fireres.core.test.AbstractTest;
import io.github.fireres.firemode.properties.FireModeProperties;
import io.github.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.github.fireres.core.test.TestUtils.toPointList;

public class FireModeReportTest extends AbstractTest {

    @Autowired
    private FireModeService fireModeService;

    @Autowired
    private FireModeProperties reportProperties;

    @Autowired
    private Sample sample;

    @Before
    public void setup() {
        sample.removeAllReports();
    }

    @Test
    public void generateFurnaceTemperature() {
        val report = fireModeService.createReport(sample, reportProperties);

        val expectedValues = toPointList(List.of(
                42, 350, 446, 503, 545, 577, 604,
                627, 646, 664, 679, 694, 706, 718,
                729, 740, 749, 758, 767, 775, 782,
                790, 797, 803, 810, 816, 821, 827,
                833, 838, 843, 848, 852, 857, 861,
                866, 870, 874, 878, 882, 886, 889,
                893, 897, 900, 903, 907, 910, 913,
                916, 919, 922, 925, 928, 931, 933,
                936, 939, 941, 944, 946, 949, 951,
                954, 956, 958, 961, 963, 965, 967,
                969
        ));

        val furnaceTemp = report.getFurnaceTemperature();

        Assert.assertEquals(expectedValues, furnaceTemp.getValue());
    }

    @Test
    public void generateMaxAllowedTemperature() {
        val report = fireModeService.createReport(sample, reportProperties);

        val expectedFunction = toPointList(List.of(
                24, 378, 489, 554, 603,
                639, 670, 697, 719, 739,
                757, 740, 754, 767, 779,
                791, 801, 811, 821, 829,
                837, 846, 854, 860, 868,
                875, 880, 887, 893, 899,
                904, 868, 873, 878, 882,
                887, 891, 896, 900, 904,
                908, 911, 916, 920, 923,
                926, 930, 933, 937, 940,
                943, 946, 949, 952, 956,
                958, 961, 964, 966, 969,
                971, 974, 977, 980, 982,
                984, 987, 989, 991, 993,
                995
        ));

        val maxAllowedTemp = report.getMaxAllowedTemperature();

        Assert.assertEquals(expectedFunction, maxAllowedTemp.getValue());
    }

    @Test
    public void generateMinAllowedTemperature() {
        val report = fireModeService.createReport(sample, reportProperties);

        val expectedFunction = toPointList(List.of(
                18, 280, 361, 410, 445,
                473, 496, 515, 531, 547,
                559, 606, 617, 627, 637,
                647, 655, 663, 671, 679,
                685, 692, 698, 704, 710,
                716, 720, 725, 731, 735,
                740, 786, 789, 794, 798,
                803, 807, 810, 814, 818,
                822, 825, 828, 832, 835,
                838, 842, 845, 847, 850,
                853, 856, 859, 862, 865,
                866, 869, 872, 874, 877,
                879, 882, 884, 886, 888,
                890, 893, 895, 897, 899,
                901
        ));

        val minAllowedTemp = report.getMinAllowedTemperature();

        Assert.assertEquals(expectedFunction, minAllowedTemp.getValue());
    }

    @Test
    public void generateStandardTemperature() {
        val report = fireModeService.createReport(sample, reportProperties);

        val expectedNumbers = toPointList(List.of(
                21, 329, 425, 482, 524, 556, 583, 606,
                625, 643, 658, 673, 685, 697, 708, 719,
                728, 737, 746, 754, 761, 769, 776, 782,
                789, 795, 800, 806, 812, 817, 822, 827,
                831, 836, 840, 845, 849, 853, 857, 861,
                865, 868, 872, 876, 879, 882, 886, 889,
                892, 895, 898, 901, 904, 907, 910, 912,
                915, 918, 920, 923, 925, 928, 930, 933,
                935, 937, 940, 942, 944, 946, 948
        ));

        val standardTemperature = report.getStandardTemperature();

        Assert.assertEquals(expectedNumbers, standardTemperature.getValue());
    }

}