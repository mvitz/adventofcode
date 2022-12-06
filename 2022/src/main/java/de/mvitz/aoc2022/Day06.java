package de.mvitz.aoc2022;

final class Day06 {

    private Day06() {
    }

    public static int firstStartOfPacketMarkerPositionOf(String input) {
        for (var i = 4; i < input.length(); i++) {
            final var lastFourCharacters = input.substring(i - 4, i);
            final var numberOfDifferentCharacters = lastFourCharacters
                    .chars()
                    .distinct()
                    .count();
            if (numberOfDifferentCharacters == 4) {
                return i;
            }
        }

        return -1;
    }
}
