package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day09.findNumberOfPositionsVisitedByTailAtLeastOnceFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day09Tests {

    @Test
    void part1_example() {
        var input = contentOf("day09_example.txt");

        var numberOfPositionsVisitedByTailAtLeastOnce = findNumberOfPositionsVisitedByTailAtLeastOnceFor(input);

        assertThat(numberOfPositionsVisitedByTailAtLeastOnce, is(13L));
    }

    @Test
    void part1() {
        var input = contentOf("day09.txt");

        var numberOfPositionsVisitedByTailAtLeastOnce = findNumberOfPositionsVisitedByTailAtLeastOnceFor(input);

        assertThat(numberOfPositionsVisitedByTailAtLeastOnce, is(6_090L));
    }
}
