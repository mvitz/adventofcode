package de.mvitz.aoc2024.utils;

import java.util.function.BiFunction;

public enum Direction {
	UP(0, -1), UP_RIGHT(+1, -1),
	RIGHT(+1, 0), DOWN_RIGHT(+1, +1),
	DOWN(0, +1), DOWN_LEFT(-1, +1),
	LEFT(-1, 0), UP_LEFT(-1, -1);

	private final BiFunction<Integer, Integer, Point> move;

	Direction(int diffX, int diffY) {
		this.move = (x, y) -> new Point(x + diffX, y + diffY);
	}

	public Direction rotate45() {
		return switch (this) {
			case UP -> UP_RIGHT;
			case UP_RIGHT -> RIGHT;
			case RIGHT -> DOWN_RIGHT;
			case DOWN_RIGHT -> DOWN;
			case DOWN -> DOWN_LEFT;
			case DOWN_LEFT -> LEFT;
			case LEFT -> UP_LEFT;
			case UP_LEFT -> UP;
		};
	}

	public Direction rotate90() {
		return rotate45().rotate45();
	}

	public Direction rotate180() {
		return rotate90().rotate90();
	}

	public Point from(Point point) {
		return move.apply(point.x(), point.y());
	}

}
