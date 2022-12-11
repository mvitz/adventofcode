package de.mvitz.aoc2022;

import java.util.ArrayList;

final class Day09 {

    private Day09() {
    }

    public static long findNumberOfPositionsVisitedByTailAtLeastOnceFor(String input, int ropeLength) {
        final var motions = input.lines()
                .map(Motion::from)
                .toList();

        var rope = Rope.ofLength(ropeLength);

        final var visitedHeadPositions = new ArrayList<Position>();
        visitedHeadPositions.add(rope.tail());

        for (final var motion : motions) {
            for (var step = 0; step < motion.count; step++) {
                rope = rope.move(motion.direction);
                visitedHeadPositions.add(rope.tail());
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

        public boolean inSameRowAs(Position other) {
            return this.y == other.y;
        }

        public boolean inSameColumnAs(Position other) {
            return this.x == other.x;
        }

        public boolean isAboveOf(Position other) {
            return y > other.y;
        }

        public boolean isRightOf(Position other) {
            return x > other.x;
        }

        public Position follow(Position other) {
            if (this.touches(other)) {
                return this;
            }

            if (this.inSameColumnAs(other) && this.isAboveOf(other)) {
                return this.move(Direction.DOWN);
            } else if (this.inSameColumnAs(other)) {
                return this.move(Direction.UP);
            } else if (this.inSameRowAs(other) && this.isRightOf(other)) {
                return this.move(Direction.LEFT);
            } else if (this.inSameRowAs(other)) {
                return this.move(Direction.RIGHT);
            } else if (this.isAboveOf(other) && this.isRightOf(other)) {
                return this.move(Direction.DOWN).move(Direction.LEFT);
            } else if (this.isAboveOf(other)) {
                return this.move(Direction.DOWN).move(Direction.RIGHT);
            } else if (this.isRightOf(other)) {
                return this.move(Direction.UP).move(Direction.LEFT);
            } else {
                return this.move(Direction.UP).move(Direction.RIGHT);
            }
        }

        public boolean touches(Position other) {
            return Math.abs(this.x - other.x) <= 1
                    && Math.abs(this.y - other.y) <= 1;
        }

        public static Position start() {
            return new Position(0, 0);
        }
    }

    private static final class Rope {

        private final Position[] knots;

        private Rope(Position[] knots) {
            this.knots = knots;
        }

        public Rope move(Direction direction) {
            final var newKnots = new Position[knots.length];
            newKnots[0] = knots[0].move(direction);

            for (int i = 1; i < knots.length; i++) {
                newKnots[i] = knots[i].follow(newKnots[i - 1]);
            }

            return new Rope(newKnots);
        }

        public Position tail() {
            return knots[knots.length - 1];
        }

        public static Rope ofLength(int length) {
            final var knots = new Position[length];
            for (int i = 0; i < knots.length; i++) {
                knots[i] = Position.start();
            }
            return new Rope(knots);
        }
    }
}
