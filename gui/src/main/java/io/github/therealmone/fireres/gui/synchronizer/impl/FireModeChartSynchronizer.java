package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.DEFAULT_MEAN_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.DEFAULT_MEAN_LINE_LEGEND_SYMBOL;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MAX_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MIN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_STANDARD_TEMPERATURE_LEGEND_SYMBOL;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_STANDARD_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addLegendSymbolId;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

public class FireModeChartSynchronizer implements ChartSynchronizer<FireModeReport> {

    public static final String STANDARD_TEMPERATURE_TEXT = "Стандартный режим пожара";
    public static final String MAX_ALLOWED_TEMPERATURE_TEXT = "Максимальный допуск температуры";
    public static final String MIN_ALLOWED_TEMPERATURE_TEXT = "Минимальный допуск температуры";
    public static final String MEAN_TEMPERATURE_TEXT = "Средняя температура";

    @Override
    public void synchronize(LineChart<Number, Number> chart, FireModeReport report) {
        chart.getData().clear();

        addThermocoupleMeanTemperatureLine(chart, report);
        addMinAllowedTemperatureLine(chart, report);
        addMaxAllowedTemperatureLine(chart, report);
        addStandardTemperatureLine(chart, report);

        addLegendSymbolId(chart, STANDARD_TEMPERATURE_TEXT, FIREMODE_STANDARD_TEMPERATURE_LEGEND_SYMBOL);
        addLegendSymbolId(chart, MEAN_TEMPERATURE_TEXT, DEFAULT_MEAN_LINE_LEGEND_SYMBOL);
    }

    private void addStandardTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(STANDARD_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getStandardTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_STANDARD_TEMPERATURE_LINE);
    }

    private void addMaxAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MAX_ALLOWED_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getMaxAllowedTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MAX_TEMPERATURE_LINE);
    }

    private void addMinAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MIN_ALLOWED_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getMinAllowedTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MIN_TEMPERATURE_LINE);
    }

    private void addThermocoupleMeanTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MEAN_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getThermocoupleMeanTemperature());
        chart.getData().add(series);
        series.getNode().setId(DEFAULT_MEAN_LINE);
    }

}
