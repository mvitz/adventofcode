package de.mvitz.aoc2022;

final class Day06 {

    private Day06() {
    }

    public static int findFirstPacketStartPositionOf(String input) {
        return findFirstStartPositionOf(input, 4);
    }

    public static int findFirstMessageStartPositionOf(String input) {
        return findFirstStartPositionOf(input, 14);
    }

    private static int findFirstStartPositionOf(String input, int markerLength) {
        for (var i = markerLength; i < input.length(); i++) {
            final var characters = input.substring(i - markerLength, i);
            final var numberOfDifferentCharacters = characters
                    .chars()
                    .distinct()
                    .count();
            if (numberOfDifferentCharacters == markerLength) {
                return i;
            }
        }

        return -1;
    }
}
