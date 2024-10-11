package de.claudioaltamura.java.examples.histogram;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HistogramTest {

    @Test
    void processData() {
        var histogram = new Histogram();

        var dataset = Arrays.asList(
                1, 5, 10,
                12, 17, 19,
                20, 23, 29);

        Map<String, Long> distributionMap = histogram.processData(dataset);
        System.out.println(distributionMap.toString());
        var key = "0-10";
        assertAll(
                () -> assertTrue(distributionMap.containsKey(key)),
                () -> assertEquals(3, distributionMap.get(key).intValue())
        );
    }
}