package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day1.findElfWithMostCaloriesFrom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day1Tests {

    @Test
    void part1_example() {
        var input = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
            """;

        var elfWithMostCalories = findElfWithMostCaloriesFrom(input);

        assertThat(elfWithMostCalories.calories(), is(24_000L));
    }
}
