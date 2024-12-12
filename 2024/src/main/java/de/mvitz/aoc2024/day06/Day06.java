package de.mvitz.aoc2024.day06;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Pair;
import de.mvitz.aoc2024.utils.Point;

import java.util.Arrays;
import java.util.HashSet;

import static de.mvitz.aoc2024.utils.Direction.*;
import static de.mvitz.aoc2024.utils.Gatherers.mapWithIndex;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class Day06 {

	private Day06() {
	}

	public static long numberOfDistinctGuardPositions(String input) {
		var grid = parseGrid(input);

		var currentDirection = UP;
		var currentPosition = grid.coordinatesWith("^").getFirst();

		var visitedPositions = new HashSet<Point>();

		var current = grid.valueAt(currentPosition);
		while (current.isPresent()) {
			var v = current.get();
			if ("#".equals(v)) {
				currentPosition = back(currentPosition, currentDirection);
				currentDirection = rotateRight(currentDirection);
			} else {
				visitedPositions.add(currentPosition);
				currentPosition = currentDirection.from(currentPosition);
			}
			current = grid.valueAt(currentPosition);
		}

		return visitedPositions.size();
	}

	private static Direction rotateRight(Direction direction) {
		if (UP.equals(direction)) {
			return RIGHT;
		} else if (RIGHT.equals(direction)) {
			return DOWN;
		} else if (DOWN.equals(direction)) {
			return LEFT;
		} else {
			return UP;
		}
	}

	private static Point back(Point current, Direction direction) {
		if (UP.equals(direction)) {
			return DOWN.from(current);
		} else if (RIGHT.equals(direction)) {
			return LEFT.from(current);
		} else if (DOWN.equals(direction)) {
			return UP.from(current);
		} else {
			return RIGHT.from(current);
		}
	}

	@SuppressWarnings("preview")
	private static Grid parseGrid(String input) {
		return new Grid(input.lines()
				.gather(mapWithIndex((y, line) ->
						Arrays.stream(line.split(""))
								.gather(mapWithIndex((x, value) ->
										Pair.of(new Point(x, y), value)))
				))
				.flatMap(identity())
				.collect(toMap(Pair::left, Pair::right)));
	}
}
