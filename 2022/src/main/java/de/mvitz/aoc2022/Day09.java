package de.mvitz.aoc2022;

import java.util.ArrayList;

final class Day09 {

    private Day09() {
    }

    public static long findNumberOfPositionsVisitedByTailAtLeastOnceFor(String input) {
        final var motions = input.lines()
                .map(Motion::from)
                .toList();

        var rope = Rope.atStart();

        final var visitedHeadPositions = new ArrayList<Position>();
        visitedHeadPositions.add(rope.tail);

        for (final var motion : motions) {
            for (var step = 0; step < motion.count; step++) {
                rope = rope.move(motion.direction);
                visitedHeadPositions.add(rope.tail);
            }
        }

        return visitedHeadPositions.stream()
                .distinct()
                .count();
    }

    private record Motion(Direction direction, int count) {

        public static Motion from(String line) {
            final var parts = line.split(" ");
            return new Motion(
                    Direction.from(parts[0]),
                    Integer.parseInt(parts[1]));
        }
    }

    private enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN;

        public static Direction from(String input) {
            return switch (input) {
                case "L" -> LEFT;
                case "U" -> UP;
                case "R" -> RIGHT;
                case "D" -> DOWN;
                default ->
                        throw new IllegalArgumentException("Invalid direction input: " + input);
            };
        }
    }

    private record Position(int x, int y) {

        public Position move(Direction direction) {
            return switch (direction) {
                case LEFT -> new Position(x - 1, y);
                case UP -> new Position(x, y + 1);
                case RIGHT -> new Position(x + 1, y);
                case DOWN -> new Position(x, y - 1);
            };
        }

        public boolean touches(Position other) {
            return Math.abs(this.x - other.x) <= 1
                    && Math.abs(this.y - other.y) <= 1;
        }

        public static Position start() {
            return new Position(0, 0);
        }
    }

    private record Rope(Position head, Position tail) {

        public Rope move(Direction direction) {
            final var newHead = head.move(direction);
            var newTail = tail;
            if (!tail.touches(newHead)) {
                newTail = head;
            }
            return new Rope(newHead, newTail);
        }

        public static Rope atStart() {
            return new Rope(Position.start(), Position.start());
        }
    }
}
