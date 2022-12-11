package de.mvitz.aoc2022;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.UnaryOperator;

import static de.mvitz.aoc2022.Day11.findLevelOfMonkeyBusinessAfterRoundsFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day11Tests {

    @Test
    void part1_example() {
        var input = contentOf("day11_example.txt");

        var levelOfMonkeyBusinessAfter20Rounds = findLevelOfMonkeyBusinessAfterRoundsFor(input, worryLevel -> worryLevel.divide(new BigInteger("3")), 20);

        assertThat(levelOfMonkeyBusinessAfter20Rounds, is(10_605L));
    }

    @Test
    void part1() {
        var input = contentOf("day11.txt");

        var levelOfMonkeyBusinessAfter20Rounds = findLevelOfMonkeyBusinessAfterRoundsFor(input, worryLevel -> worryLevel.divide(new BigInteger("3")), 20);

        assertThat(levelOfMonkeyBusinessAfter20Rounds, is(57_838L));
    }

    @Test
    @Disabled
    void part2_example() {
        var input = contentOf("day11_example.txt");

        var levelOfMonkeyBusinessAfter20Rounds = findLevelOfMonkeyBusinessAfterRoundsFor(input, UnaryOperator.identity(), 10_000);

        assertThat(levelOfMonkeyBusinessAfter20Rounds, is(2_713_310_158L));
    }
}
