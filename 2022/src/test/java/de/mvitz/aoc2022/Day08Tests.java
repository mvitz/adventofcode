package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day08.findHighestScenicScoreFor;
import static de.mvitz.aoc2022.Day08.findNumberOfVisibleTreesFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day08Tests {

    @Test
    void part1_example() {
        var input = contentOf("day08_example.txt");

        var numberOfVisibleTrees = findNumberOfVisibleTreesFor(input);

        assertThat(numberOfVisibleTrees, is(21L));
    }

    @Test
    void part1() {
        var input = contentOf("day08.txt");

        var numberOfVisibleTrees = findNumberOfVisibleTreesFor(input);

        assertThat(numberOfVisibleTrees, is(1_829L));
    }

    @Test
    void part2_example() {
        var input = contentOf("day08_example.txt");

        var highestScenicScore = findHighestScenicScoreFor(input);

        assertThat(highestScenicScore, is(8L));
    }
}
