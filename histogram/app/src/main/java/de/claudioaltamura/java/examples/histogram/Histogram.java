package de.claudioaltamura.java.examples.histogram;

import org.apache.commons.math3.stat.Frequency;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Histogram {

    private final Map<String,Long> distributionMap; //K,V verbessern?
    private final int classWidth;

    public Histogram() {
        this.classWidth = 10;
        this.distributionMap = new TreeMap<>();
    }

    public Map<String,Long> processData(List<Integer> data) {
        Objects.requireNonNull(data);

        var frequency = calculateFrequency(data);

        data.stream()
                .map(Double::valueOf)
                .distinct()
                .forEach(observation -> {
                    long observationFrequency = frequency.getCount(observation);
                    int upperBoundary = (observation > classWidth)
                            ? Math.multiplyExact( (int) Math.ceil(observation / classWidth), classWidth)
                            : classWidth;
                    int lowerBoundary = (upperBoundary > classWidth)
                            ? Math.subtractExact(upperBoundary, classWidth)
                            : 0;
                    String bin = lowerBoundary + "-" + upperBoundary;
                    updateDistributionMap(lowerBoundary, bin, observationFrequency);
                });

        return distributionMap;
    }

    private Frequency calculateFrequency(List<Integer> data) {
        var frequency = new Frequency();
        data.forEach(d -> frequency.addValue(Double.parseDouble(d.toString())));
        return frequency;
    }

    private void updateDistributionMap(int lowerBoundary, String bin, long observationFrequency) {
        int prevLowerBoundary = (lowerBoundary > classWidth) ? lowerBoundary - classWidth : 0;
        String prevBin = prevLowerBoundary + "-" + lowerBoundary;
        if(prevLowerBoundary != lowerBoundary && !distributionMap.containsKey(prevBin))
            distributionMap.put(prevBin, 0L);
        if(!distributionMap.containsKey(bin)) {
            distributionMap.put(bin, observationFrequency);
        }
        else {
            long oldFrequency = distributionMap.get(bin);
            distributionMap.replace(bin, oldFrequency + observationFrequency);
        }
    }
}
