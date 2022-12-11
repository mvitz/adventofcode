package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day10.findSumOfRequiredSignalStrengthsFrom;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day10Tests {

    @Test
    void part1_example() {
        var input = contentOf("day10_example.txt");

        var sumOfRequiredSignalStrengths = findSumOfRequiredSignalStrengthsFrom(input);

        assertThat(sumOfRequiredSignalStrengths, is(13_140));
    }

    @Test
    void part1() {
        var input = contentOf("day10.txt");

        var sumOfRequiredSignalStrengths = findSumOfRequiredSignalStrengthsFrom(input);

        assertThat(sumOfRequiredSignalStrengths, is(14_760));
    }
}
