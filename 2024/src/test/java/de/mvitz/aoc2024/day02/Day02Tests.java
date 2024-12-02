package de.mvitz.aoc2024.day02;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day02.Day02.findNumberOfSafeReportsFrom;
import static org.assertj.core.api.Assertions.assertThat;

class Day02Tests {

    @Test
    @Order(0)
    void part1_example() {
        // given
        var input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
                """;

        // when
        var solution = findNumberOfSafeReportsFrom(input);

        // then
        assertThat(solution)
                .isEqualTo(2);
    }

    @Test
    @Order(1)
    void part1() {
        // given
        var input = Files.contentOf("day02/part1.txt");

        // when
        var solution = findNumberOfSafeReportsFrom(input);

        // then
        assertThat(solution)
                .isEqualTo(269);
    }
}
