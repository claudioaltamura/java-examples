package de.claudioaltamura.java.examples.histogram;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HistogramChart {

    public static void main(String[] args) {
        var histogram = new Histogram();
        var dataset = Arrays.asList(
                36, 25, 38, 46, 55, 68, 72,
                55, 36, 38, 67, 45, 22, 48,
                91, 46, 52, 61, 58, 55);
        Map<String, Long> distributionMap = histogram.processData(dataset);

        var yData = new ArrayList<>(distributionMap.values());
        var xData = Arrays.asList(distributionMap.keySet().toArray());
        CategoryChart chart = buildChart(xData, yData);

        new SwingWrapper<>(chart).displayChart();
    }

    private static CategoryChart buildChart(List<Object> xData, List<Long> yData) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("Age Distribution")
                .xAxisTitle("Age Group")
                .yAxisTitle("Frequency")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(0.99);
        chart.getStyler().setOverlapped(true);

        chart.addSeries("age group", xData, yData);

        return chart;
    }

}