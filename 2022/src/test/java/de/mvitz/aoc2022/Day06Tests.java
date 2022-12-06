package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2022.Day06.firstStartOfMessageMarkerPositionOf;
import static de.mvitz.aoc2022.Day06.firstStartOfPacketMarkerPositionOf;
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
    void part1_example(String input, int expectedFirstStartOfPacketMarkerPosition) {
        var firstStartOfPacketMarkerPosition = firstStartOfPacketMarkerPositionOf(input);

        assertThat(firstStartOfPacketMarkerPosition, is(expectedFirstStartOfPacketMarkerPosition));
    }

    @Test
    void part1() {
        var input = firstLineOf("day06.txt");

        var firstStartOfPacketMarkerPosition = firstStartOfPacketMarkerPositionOf(input);

        assertThat(firstStartOfPacketMarkerPosition, is(1_093));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            mjqjpqmgbljsphdztnvjfqwrcgsmlb,19
            bvwbjplbgvbhsrlpgdmjqwftvncz,23
            nppdvjthqldpwncqszvftbrmjlhg,23
            nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29
            zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26
                        """)
    void part2_example(String input, int expectedFirstStartOfMessageMarkerPosition) {
        var firstStartOfMessageMarkerPosition = firstStartOfMessageMarkerPositionOf(input);

        assertThat(firstStartOfMessageMarkerPosition, is(expectedFirstStartOfMessageMarkerPosition));
    }

    @Test
    void part2() {
        var input = firstLineOf("day06.txt");

        var firstStartOfMessageMarkerPosition = firstStartOfMessageMarkerPositionOf(input);

        assertThat(firstStartOfMessageMarkerPosition, is(3_534));
    }
}
