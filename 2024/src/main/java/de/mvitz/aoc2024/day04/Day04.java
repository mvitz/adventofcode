package de.mvitz.aoc2024.day04;

import java.util.List;
import java.util.Set;

import static de.mvitz.aoc2024.utils.Direction.*;

public final class Day04 {

	private Day04() {
	}

	public static long findNumberOfXmasAppearancesIn(String input) {
		var directions = List.of(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT);
		var wordSearch = WordSearch.from(input);
		return wordSearch.coordinatesWith("X").stream()
				.mapToLong(x -> directions.stream()
						.filter(direction -> "XMAS".equals(wordSearch.wordAt(x, direction, 4)))
						.count())
				.sum();
	}

	public static long findNumberOfXDashMasAppearancesIn(String input) {
		var mas = Set.of("MAS", "SAM");
		var wordSearch = WordSearch.from(input);
		return wordSearch.coordinatesWith("A").stream()
				.filter(a -> mas.contains(wordSearch.wordAt(UP_LEFT.from(a), DOWN_RIGHT, 3)))
				.filter(a -> mas.contains(wordSearch.wordAt(DOWN_LEFT.from(a), UP_RIGHT, 3)))
				.count();
	}
}
