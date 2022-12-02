package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day02.calculateScoreFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day02Tests {

    @Test
    void part1_example() {
        var input = contentOf("day02_example.txt");

        var score = calculateScoreFor(input, Day02::firstGuideScoreForRound);

        assertThat(score, is(15));
    }

    @Test
    void part1() {
        var input = contentOf("day02.txt");

        var score = calculateScoreFor(input, Day02::firstGuideScoreForRound);

        assertThat(score, is(13_446));
    }

    @Test
    void part2_example() {
        var input = contentOf("day02_example.txt");

        var score = calculateScoreFor(input, Day02::secondGuideScoreForRound);

        assertThat(score, is(12));
    }

    @Test
    void part2() {
        var input = contentOf("day02.txt");

        var score = calculateScoreFor(input, Day02::secondGuideScoreForRound);

        assertThat(score, is(13_509));
    }
}
