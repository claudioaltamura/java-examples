package de.claudioaltamura.java.examples.histogram;

import org.apache.commons.math3.stat.Frequency;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Histogram {

    private Map<String,Long> distributionMap; //K,V verbessern?
    private int classWidth;

    public Histogram() {
        distributionMap = new TreeMap<>();
        classWidth = 10;
    }

    public Map<String,Long> processData(List<Integer> data) {
        Objects.requireNonNull(data);

        var frequency = calculateFrequency(data);

        data.stream()
                .map(d -> Double.parseDouble(d.toString()))//verbessern?
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
        data.forEach(d -> frequency.addValue(Double.parseDouble(d.toString()))); //verbessern?
        return frequency;
    }

    private void updateDistributionMap(int lowerBoundary, String bin, long observationFrequency) {
        int prevLowerBoundary = (lowerBoundary > classWidth) ? lowerBoundary - classWidth : 0;
        String prevBin = prevLowerBoundary + "-" + lowerBoundary;
        if(!distributionMap.containsKey(prevBin))
            distributionMap.put(prevBin, 0L);
        if(!distributionMap.containsKey(bin)) {
            distributionMap.put(bin, observationFrequency);
        }
        else {
            long oldFrequency = Long.parseLong(distributionMap.get(bin).toString());
            distributionMap.replace(bin, oldFrequency + observationFrequency);
        }
    }
}
