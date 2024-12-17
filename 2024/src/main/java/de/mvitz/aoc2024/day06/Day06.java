package de.mvitz.aoc2024.day06;

import de.mvitz.aoc2024.day06.Day06.Solution.Loop;
import de.mvitz.aoc2024.day06.Day06.Solution.Step;
import de.mvitz.aoc2024.day06.Day06.Solution.WalkOff;
import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static de.mvitz.aoc2024.utils.Direction.UP;
import static java.util.function.Predicate.not;

public final class Day06 {

	private Day06() {
	}

	public static long numberOfDistinctGuardPositions(String input) {
		var grid = Grid.from(input);

		var solution = walkthrough(grid);

		if (!(solution instanceof WalkOff(var path))) {
			throw new IllegalArgumentException("Guard never walked off the grid!");
		}

		return path.stream()
				.map(Step::from)
				.distinct()
				.count();
	}

	static long numberOfObstructionPositionsToProduceLoopFor(String input) {
		var grid = Grid.from(input);

		var initialWalkOff = walkthrough(grid);

		var startingPosition = initialWalkOff.path().getFirst().from();

		return initialWalkOff.path().stream()
				.map(Step::to)
				.filter(not(startingPosition::equals)) // obstacle can not be placed on starting position
				.filter(to -> grid.valueAt(to).isPresent()) // obstacle can not be placed out of bounds
				.distinct()
				.parallel()
				.map(to -> grid.with(to, "O")) // place obstacle on every possible position
				.map(Day06::walkthrough) // walk through every relevant grid with obstacle
				.filter(Loop.class::isInstance) // find all loops
				.count();
	}

	private static Solution walkthrough(Grid grid) {
		var currentDirection = UP;
		var currentPosition = grid.coordinatesWith("^").getFirst();

		var path = new ArrayList<Step>();

		var nextPosition = currentDirection.from(currentPosition);
		var next = grid.valueAt(nextPosition);
		while (next.isPresent()) {
			if ("#".equals(next.get()) || "O".equals(next.get())) {
				currentDirection = currentDirection.rotate90();
			} else {
				var step = new Step(currentPosition, nextPosition);
				if (path.contains(step)) {
					// loop
					return new Loop(path);
				}
				path.add(step);
				currentPosition = nextPosition;
			}
			nextPosition = currentDirection.from(currentPosition);
			next = grid.valueAt(nextPosition);
		}

		path.add(new Step(currentPosition, nextPosition));

		return new WalkOff(path);
	}

	sealed interface Solution {


		record Step(Point from, Point to) {
		}

		List<Step> path();

		record WalkOff(List<Step> path) implements Solution {
		}

		record Loop(List<Step> path) implements Solution {
		}
	}
}
