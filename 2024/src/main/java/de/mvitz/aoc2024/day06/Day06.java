package de.mvitz.aoc2024.day06;

import de.mvitz.aoc2024.day06.Day06.Map.Path;
import de.mvitz.aoc2024.day06.Day06.Map.Path.Step;
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
		return Map.from(input)
				.predictGuardRoute()
				.visitedPositions()
				.size();
	}

	static long numberOfObstructionPositionsToProduceLoopFor(String input) {
		var map = Map.from(input);

		var originalPath = map.predictGuardRoute();

		return originalPath.steps().stream()
				.map(Step::to)
				.filter(not(originalPath::startsAt)) // obstacle can not be placed on starting position
				.filter(map::isInBounds) // obstacle can not be placed out of bounds
				.distinct()
				.parallel()
				.map(map::withObstacleAt) // place obstacle on every possible position
				.map(Map::predictGuardRoute) // walk through every relevant grid with obstacle
				.filter(Path::isLoop) // find all loops
				.count();
	}

	record Map(Grid grid) {

		private static final String GUARD = "^";
		private static final String OBSTRUCTION = "#";

		record Path(List<Step> steps) {

			record Step(Point from, Point to) {
			}

			public Point start() {
				return steps.getFirst().from;
			}

			public boolean startsAt(Point position) {
				return start().equals(position);
			}

			public boolean isLoop() {
				return visitedPositions().contains(end());
			}

			private Point end() {
				return steps.getLast().to;
			}

			public List<Point> visitedPositions() {
				return steps.stream()
						.map(Step::from)
						.distinct()
						.toList();
			}
		}

		public Path predictGuardRoute() {
			var currentDirection = UP;
			var currentPosition = grid.coordinatesWith(GUARD).getFirst();

			var steps = new ArrayList<Step>();

			var nextPosition = currentDirection.from(currentPosition);
			var next = grid.valueAt(nextPosition);
			while (next.isPresent()) {
				if (OBSTRUCTION.equals(next.get())) {
					currentDirection = currentDirection.rotate90();
				} else {
					var step = new Step(currentPosition, nextPosition);
					if (steps.contains(step)) {
						// loop
						return new Path(steps);
					}
					steps.add(step);
					currentPosition = nextPosition;
				}
				nextPosition = currentDirection.from(currentPosition);
				next = grid.valueAt(nextPosition);
			}

			steps.add(new Step(currentPosition, nextPosition));

			return new Path(steps);
		}

		public boolean isInBounds(Point position) {
			return grid.valueAt(position).isPresent();
		}

		public Map withObstacleAt(Point position) {
			return new Map(grid.with(position, OBSTRUCTION));
		}

		public static Map from(String input) {
			return new Map(Grid.from(input));
		}
	}
}
