package de.mvitz.aoc2022;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

final class Day06Regex {

    private Day06Regex() {
    }

    public static int findFirstPacketStartPositionOf(String input) {
        return findFirstStartPositionOf(input, 4);
    }

    public static int findFirstMessageStartPositionOf(String input) {
        return findFirstStartPositionOf(input, 14);
    }

    private static int findFirstStartPositionOf(String input, int markerLength) {
        return generatePatternFor(markerLength)
                .matcher(input)
                .results()
                .mapToInt(match -> match.start(markerLength + 2))
                .findFirst()
                .orElse(-1);
    }

    private static Pattern generatePatternFor(int markerLength) {
        final var regex = new StringBuilder("(")
                .append(IntStream.range(0, markerLength)
                        .mapToObj(Day06Regex::markerCharGroup)
                        .collect(joining("")))
                .append(")(.*)")
                .toString();
        return Pattern.compile(regex, Pattern.MULTILINE);
    }

    private static String markerCharGroup(int markerCharPosition) {
        if (markerCharPosition == 0) {
            return "(.)";
        }
        return new StringBuilder("((?!")
                .append(IntStream.range(0, markerCharPosition)
                        .map(pos -> pos + 2)
                        .mapToObj(String::valueOf)
                        .map("\\"::concat)
                        .collect(joining("|")))
                .append(").)")
                .toString();
    }
}
