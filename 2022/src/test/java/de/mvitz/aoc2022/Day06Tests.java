package de.mvitz.aoc2022;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2022.Day06.firstMarkPositionOf;
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
    void part1_example(String input, int expectedFirstMarkerPosition) {
        var firstMarkerPosition = firstMarkPositionOf(input);

        assertThat(firstMarkerPosition, is(expectedFirstMarkerPosition));
    }
}