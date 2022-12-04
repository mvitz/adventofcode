package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day04.numberOfFullyContainedAssignmentsFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day04Tests {

    @Test
    void part1_example() {
        var input = contentOf("day04_example.txt");

        var numberOfFullyContainedAssignments = numberOfFullyContainedAssignmentsFor(input);

        assertThat(numberOfFullyContainedAssignments, is(2L));
    }
}
