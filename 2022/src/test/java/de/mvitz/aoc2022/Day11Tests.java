package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day11.findLevelOfMonkeyBusinessAfter20RoundsFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day11Tests {

    @Test
    void part1_example() {
        var input = contentOf("day11_example.txt");

        var levelOfMonkeyBusinessAfter20Rounds = findLevelOfMonkeyBusinessAfter20RoundsFor(input);

        assertThat(levelOfMonkeyBusinessAfter20Rounds, is(10_605));
    }

    @Test
    void part1() {
        var input = contentOf("day11.txt");

        var levelOfMonkeyBusinessAfter20Rounds = findLevelOfMonkeyBusinessAfter20RoundsFor(input);

        assertThat(levelOfMonkeyBusinessAfter20Rounds, is(57_838));
    }
}
