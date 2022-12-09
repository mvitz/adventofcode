package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day07.findSumOfTotalSizeOfDirectoriesBelow100000;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day07Tests {

    @Test
    void part1_example() {
        var input = contentOf("day07_example.txt");

        var sumOfTotalSizeOfDirectoriesBelow100000 = findSumOfTotalSizeOfDirectoriesBelow100000(input);

        assertThat(sumOfTotalSizeOfDirectoriesBelow100000, is(95_437));
    }
}
