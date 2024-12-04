package de.mvitz.aoc2024.utils;

import java.util.function.BiFunction;

public record Direction(BiFunction<Integer, Integer, Point> move) {

	public static final Direction UP = new Direction((x, y) -> new Point(x, y - 1));
	public static final Direction RIGHT = new Direction((x, y) -> new Point(x + 1, y));
	public static final Direction DOWN = new Direction((x, y) -> new Point(x, y + 1));
	public static final Direction LEFT = new Direction((x, y) -> new Point(x - 1, y));

	public static final Direction UP_RIGHT = UP.then(RIGHT);
	public static final Direction DOWN_RIGHT = DOWN.then(RIGHT);
	public static final Direction DOWN_LEFT = DOWN.then(LEFT);
	public static final Direction UP_LEFT = UP.then(LEFT);

	public Point from(Point point) {
		return move.apply(point.x(), point.y());
	}

	public Direction then(Direction other) {
		return new Direction(this.move
				.andThen(point -> other.move.apply(point.x(), point.y())));
	}
}
