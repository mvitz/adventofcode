package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day07.findSumOfTotalSizeOfDirectoriesBelow100000;
import static de.mvitz.aoc2022.Day07.findTotalSizeOfSmallestDirectoryToDeleteFor;
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

    @Test
    void part1() {
        var input = contentOf("day07.txt");

        var sumOfTotalSizeOfDirectoriesBelow100000 = findSumOfTotalSizeOfDirectoriesBelow100000(input);

        assertThat(sumOfTotalSizeOfDirectoriesBelow100000, is(1_513_699));
    }

    @Test
    void part2_example() {
        var input = contentOf("day07_example.txt");

        var totalSizeOfSmallestDirectoryToDelete = findTotalSizeOfSmallestDirectoryToDeleteFor(input);

        assertThat(totalSizeOfSmallestDirectoryToDelete, is(24_933_642));
    }
}
