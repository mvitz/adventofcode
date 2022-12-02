package de.mvitz.aoc2022;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

final class Day02Enum {

    private Day02Enum() {
    }

    public static int calculateScoreFor(
            String input,
            ToIntBiFunction<Shape, String> strategy) {
        return input
                .lines()
                .mapToInt(round -> {
                    final var symbols = round.split(" ");
                    return strategy.applyAsInt(Shape.from(symbols[0]), symbols[1]);
                })
                .sum();
    }

    public static int firstGuideScoreForRound(
            Shape opponentShape,
            String secondSymbol) {
        final var myShape = Shape.from(secondSymbol);
        final var result = myShape.play(opponentShape);
        return result.score() + myShape.score();
    }

    public static int secondGuideScoreForRound(
            Shape opponentShape,
            String secondSymbol) {
        final var result = Result.from(secondSymbol);
        final var myShape = opponentShape.shapeToAchieve(result);
        return result.score() + myShape.score();
    }

    enum Result {
        WIN,
        LOSS,
        DRAW;

        public int score() {
            return switch (this) {
                case WIN -> 6;
                case LOSS -> 0;
                case DRAW -> 3;
            };
        }

        public static Result from(String symbol) {
            return switch (symbol) {
                case "X" -> Result.LOSS;
                case "Y" -> Result.DRAW;
                case "Z" -> Result.WIN;
                default ->
                        throw new IllegalArgumentException("Unknown result: " + symbol);
            };
        }
    }

    enum Shape {
        ROCK,
        PAPER,
        SCISSORS;

        public int score() {
            return switch (this) {
                case ROCK -> 1;
                case PAPER -> 2;
                case SCISSORS -> 3;
            };
        }

        public Result play(Shape opponent) {
            return switch (this) {
                case ROCK -> switch (opponent) {
                    case ROCK -> Result.DRAW;
                    case PAPER -> Result.LOSS;
                    case SCISSORS -> Result.WIN;
                };
                case PAPER -> switch (opponent) {
                    case ROCK -> Result.WIN;
                    case PAPER -> Result.DRAW;
                    case SCISSORS -> Result.LOSS;
                };
                case SCISSORS -> switch (opponent) {
                    case ROCK -> Result.LOSS;
                    case PAPER -> Result.WIN;
                    case SCISSORS -> Result.DRAW;
                };
            };
        }

        public Shape shapeToAchieve(Result result) {
            return Arrays.stream(Shape.values())
                    .filter(opponent -> opponent.play(this) == result)
                    .findFirst()
                    .orElseThrow();
        }

        public static Shape from(String symbol) {
            return switch (symbol) {
                case "A", "X" -> Shape.ROCK;
                case "B", "Y" -> Shape.PAPER;
                case "C", "Z" -> Shape.SCISSORS;
                default ->
                        throw new IllegalArgumentException("Unknown shape: " + symbol);
            };
        }
    }
}
