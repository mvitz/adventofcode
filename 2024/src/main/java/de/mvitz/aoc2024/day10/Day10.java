package de.mvitz.aoc2024.day10;

import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Pair;
import de.mvitz.aoc2024.utils.Point;

import java.util.List;
import java.util.stream.Stream;

import static de.mvitz.aoc2024.utils.Direction.*;
import static java.util.stream.Collectors.toSet;

public final class Day10 {

	private Day10() {
	}

	public static long sumOfTrailheadScoresFor(String input) {
		return TopographicMap.from(input)
				.hikingTrails().stream()
				.map(trail -> Pair.of(trail.head(), trail.nineHeightPosition()))
				.distinct()
				.count();
	}

	public static long sumOfTrailheadRatingsFor(String input) {
		return TopographicMap.from(input)
				.hikingTrails()
				.size();
	}

	private record TopographicMap(Grid<Integer> grid) {

		private record HikingTrail(List<Point> route) {

			public Point head() {
				return route.getFirst();
			}

			public Point nineHeightPosition() {
				return route.getLast();
			}
		}

		public List<HikingTrail> hikingTrails() {
			return grid.coordinatesWith(0).stream()
					.flatMap(head -> nextStepsFrom(head).stream()
							.flatMap(first -> nextStepsFrom(first).stream()
									.flatMap(second -> nextStepsFrom(second).stream()
											.flatMap(third -> nextStepsFrom(third).stream()
													.flatMap(fourth -> nextStepsFrom(fourth).stream()
															.flatMap(fifth -> nextStepsFrom(fifth).stream()
																	.flatMap(sixth -> nextStepsFrom(sixth).stream()
																			.flatMap(seventh -> nextStepsFrom(seventh).stream()
																					.flatMap(eight -> nextStepsFrom(eight).stream()
																							.map(ninth -> List.of(head, first, second, third, fourth, fifth, sixth, seventh, eight, ninth)))))))))))
					.map(HikingTrail::new)
					.toList();
		}

		private List<Point> nextStepsFrom(Point point) {
			var connectedPoints = Stream.of(UP, RIGHT, DOWN, LEFT)
					.map(direction -> direction.from(point))
					.collect(toSet());

			var level = grid.valueAt(point).orElseThrow();
			return grid.coordinatesWith(level + 1)
					.stream()
					.filter(connectedPoints::contains)
					.toList();
		}

		public static TopographicMap from(String input) {
			return new TopographicMap(Grid.from(input, Integer::parseInt));
		}
	}
}
