package de.mvitz.aoc2024.day09;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day09.Day09.filesystemChecksumAfterCompactionOf;
import static de.mvitz.aoc2024.day09.Day09.filesystemChecksumAfterFragmentationOf;
import static org.assertj.core.api.Assertions.assertThat;

class Day09Tests {

    @Test
    @Order(0)
    void part1_example() {
        // given
        var input = "2333133121414131402";

        // when
        var solution = filesystemChecksumAfterFragmentationOf(input);

        // then
        assertThat(solution)
                .isEqualTo(1_928);
    }

    @Test
    @Order(1)
    void part1() {
        // given
        var input = Files.contentOf("day09/input.txt").lines().findFirst().orElseThrow();

        // when
        var solution = filesystemChecksumAfterFragmentationOf(input);

        // then
        assertThat(solution)
                .isEqualTo(6_448_989_155_953L);
    }

    @Test
    @Order(2)
    void part2_example() {
        // given
        var input = "2333133121414131402";

        // when
        var solution = filesystemChecksumAfterCompactionOf(input);

        // then
        assertThat(solution)
                .isEqualTo(2_858);
    }

    @Test
    @Order(3)
    void part2() {
        // given
        var input = Files.contentOf("day09/input.txt").lines().findFirst().orElseThrow();

        // when
        var solution = filesystemChecksumAfterCompactionOf(input);

        // then
        assertThat(solution)
                .isEqualTo(6_476_642_796_832L);
    }
}
