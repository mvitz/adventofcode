package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day02.calculateFirstGuideScoreFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day02Tests {

    @Test
    void part1_example() {
        var input = contentOf("day02_example.txt");

        var score = calculateFirstGuideScoreFor(input);

        assertThat(score, is(15));
    }

    @Test
    void part1() {
        var input = contentOf("day02.txt");

        var score = calculateFirstGuideScoreFor(input);

        assertThat(score, is(13_446));
    }
}
