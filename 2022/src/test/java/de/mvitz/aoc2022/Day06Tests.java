package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2022.Day06.findFirstMessageStartPositionOf;
import static de.mvitz.aoc2022.Day06.findFirstPacketStartPositionOf;
import static de.mvitz.aoc2022.Utils.firstLineOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day06Tests {

    @ParameterizedTest
    @CsvSource(textBlock = """
            bvwbjplbgvbhsrlpgdmjqwftvncz,5
            nppdvjthqldpwncqszvftbrmjlhg,6
            nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10
            zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11
                        """)
    void part1_example(String input, int expectedFirstPacketStartPosition) {
        var firstPacketStartPosition = findFirstPacketStartPositionOf(input);

        assertThat(firstPacketStartPosition, is(expectedFirstPacketStartPosition));
    }

    @Test
    void part1() {
        var input = firstLineOf("day06.txt");

        var firstPacketStartPosition = findFirstPacketStartPositionOf(input);

        assertThat(firstPacketStartPosition, is(1_093));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            mjqjpqmgbljsphdztnvjfqwrcgsmlb,19
            bvwbjplbgvbhsrlpgdmjqwftvncz,23
            nppdvjthqldpwncqszvftbrmjlhg,23
            nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29
            zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26
                        """)
    void part2_example(String input, int expectedFirstMessageStartPosition) {
        var firstMessageStartPosition = findFirstMessageStartPositionOf(input);

        assertThat(firstMessageStartPosition, is(expectedFirstMessageStartPosition));
    }

    @Test
    void part2() {
        var input = firstLineOf("day06.txt");

        var firstMessageStartPosition = findFirstMessageStartPositionOf(input);

        assertThat(firstMessageStartPosition, is(3_534));
    }
}
