package de.mvitz.aoc2024.day09;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

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
}
