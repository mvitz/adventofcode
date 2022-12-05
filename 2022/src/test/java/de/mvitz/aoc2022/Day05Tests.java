package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day05.findCrateOnTopMessageFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class Day05Tests {

    @Test
    void part1_example() {
        var input = contentOf("day05_example.txt");

        var crateOnTopMessage = findCrateOnTopMessageFor(9000, input);

        assertThat(crateOnTopMessage, is(equalTo("CMZ")));
    }

    @Test
    void part1() {
        var input = contentOf("day05.txt");

        var crateOnTopMessage = findCrateOnTopMessageFor(9000, input);

        assertThat(crateOnTopMessage, is(equalTo("LJSVLTWQM")));
    }

    @Test
    void part2_example() {
        var input = contentOf("day05_example.txt");

        var crateOnTopMessage = findCrateOnTopMessageFor(9001, input);

        assertThat(crateOnTopMessage, is(equalTo("MCD")));
    }

    @Test
    void part2() {
        var input = contentOf("day05.txt");

        var crateOnTopMessage = findCrateOnTopMessageFor(9001, input);

        assertThat(crateOnTopMessage, is(equalTo("BRQWDBBJM")));
    }
}
