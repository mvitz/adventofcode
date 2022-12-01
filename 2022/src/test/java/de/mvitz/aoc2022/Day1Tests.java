package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day1.findMostCaloriesOfSingleElf;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day1Tests {

    @Test
    void part1_example() {
        var input = contentOf("day01_example.txt");

        var mostCaloriesOfSingleElf = findMostCaloriesOfSingleElf(input);

        assertThat(mostCaloriesOfSingleElf, is(24_000L));
    }

    @Test
    void part1() {
        var input = contentOf("day01.txt");

        var mostCaloriesOfSingleElf = findMostCaloriesOfSingleElf(input);

        assertThat(mostCaloriesOfSingleElf, is(69_626L));
    }
}
