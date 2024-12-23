package de.mvitz.aoc2024.day04;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.mvitz.aoc2024.utils.Direction.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;

public final class Day04 {

	private Day04() {
	}

	public static long findNumberOfXmasAppearancesIn(String input) {
		var directions = List.of(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT);
		var wordSearch = Grid.from(input, identity());
		return wordSearch.coordinatesWith("X").stream()
				.mapToLong(x -> directions.stream()
						.filter(direction -> "XMAS".equals(wordAt(wordSearch, x, direction, 4)))
						.count())
				.sum();
	}

	public static long findNumberOfXDashMasAppearancesIn(String input) {
		var mas = Set.of("MAS", "SAM");
		var wordSearch = Grid.from(input, identity());
		return wordSearch.coordinatesWith("A").stream()
				.filter(a -> mas.contains(wordAt(wordSearch, UP_LEFT.from(a), DOWN_RIGHT, 3)))
				.filter(a -> mas.contains(wordAt(wordSearch, DOWN_LEFT.from(a), UP_RIGHT, 3)))
				.count();
	}

	private static String wordAt(Grid<String> grid, Point start, Direction direction, int length) {
		var values = new ArrayList<Optional<String>>();

		var coordinate = start;
		for (var step = 0; step < length; step++) {
			values.add(grid.valueAt(coordinate));
			coordinate = direction.from(coordinate);
		}

		return values.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(joining());
	}
}
