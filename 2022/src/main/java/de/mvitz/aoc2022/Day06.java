package de.mvitz.aoc2022;

final class Day06 {

    private Day06() {
    }

    public static int firstStartOfPacketMarkerPositionOf(String input) {
        return findMarkerPositionOf(input, 4);
    }

    public static int firstStartOfMessageMarkerPositionOf(String input) {
        return findMarkerPositionOf(input, 14);
    }

    private static int findMarkerPositionOf(String input, int markerLength) {
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
