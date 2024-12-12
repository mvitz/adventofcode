package de.mvitz.aoc2024.day06;

import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Point;

import java.util.HashSet;

import static de.mvitz.aoc2024.utils.Direction.UP;

public final class Day06 {

	private Day06() {
	}

	public static long numberOfDistinctGuardPositions(String input) {
		var grid = Grid.from(input);

		var currentDirection = UP;
		var currentPosition = grid.coordinatesWith("^").getFirst();

		var visitedPositions = new HashSet<Point>();

		var current = grid.valueAt(currentPosition);
		while (current.isPresent()) {
			var v = current.get();
			if ("#".equals(v)) {
				currentPosition = currentDirection.rotate180().from(currentPosition);
				currentDirection = currentDirection.rotate90();
			} else {
				visitedPositions.add(currentPosition);
				currentPosition = currentDirection.from(currentPosition);
			}
			current = grid.valueAt(currentPosition);
		}

		return visitedPositions.size();
	}
}
