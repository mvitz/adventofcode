package de.mvitz.aoc2022;

import java.util.function.ToIntFunction;

final class Day02 {

    private Day02() {
    }

    public static int calculateScoreFor(
            String input,
            ToIntFunction<String> strategy) {
        return input
                .lines()
                .mapToInt(strategy)
                .sum();
    }

    // A for Rock, B for Paper, and C for Scissors
    // X for Rock, Y for Paper, and Z for Scissors
    // 1 for Rock, 2 for Paper, and 3 for Scissors
    // 0 if you lost, 3 if the round was a draw, and 6 if you won
    public static int firstGuideScoreForRound(String round) {
        return switch (round) {
            case "A X" -> 3 + 1;
            case "A Y" -> 6 + 2;
            case "A Z" -> 0 + 3;

            case "B X" -> 0 + 1;
            case "B Y" -> 3 + 2;
            case "B Z" -> 6 + 3;

            case "C X" -> 6 + 1;
            case "C Y" -> 0 + 2;
            case "C Z" -> 3 + 3;

            default ->
                    throw new IllegalArgumentException("Unknown round: " + round);
        };
    }

    // A for Rock, B for Paper, and C for Scissors
    // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
    // 1 for Rock, 2 for Paper, and 3 for Scissors
    // 0 if you lost, 3 if the round was a draw, and 6 if you won
    public static int secondGuideScoreForRound(String round) {
        return switch (round) {
            case "A X" -> 0 + 3;
            case "A Y" -> 3 + 1;
            case "A Z" -> 6 + 2;

            case "B X" -> 0 + 1;
            case "B Y" -> 3 + 2;
            case "B Z" -> 6 + 3;

            case "C X" -> 0 + 2;
            case "C Y" -> 3 + 3;
            case "C Z" -> 6 + 1;

            default ->
                    throw new IllegalArgumentException("Unknown round: " + round);
        };
    }
}
