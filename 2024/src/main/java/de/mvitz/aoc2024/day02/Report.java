package de.mvitz.aoc2024.day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;

public final class Report {

	private final List<Integer> levels;

	private Report(List<Integer> levels) {
		this.levels = levels;
	}

	public boolean isSafe() {
		return (areAllLevelsIncreasing() || areAllLevelsDecreasing())
			   && areAllLevelDifferencesInRange();
	}

	private boolean areAllLevelsIncreasing() {
		return levels.stream()
				.gather(Gatherers.windowSliding(2))
				.allMatch(pair -> pair.getFirst() < pair.getLast());
	}

	private boolean areAllLevelsDecreasing() {
		return levels.stream()
				.gather(Gatherers.windowSliding(2))
				.allMatch(pair -> pair.getFirst() > pair.getLast());
	}

	private boolean areAllLevelDifferencesInRange() {
		return levels.stream()
				.gather(Gatherers.windowSliding(2))
				.allMatch(pair -> areInRange(pair.getFirst(), pair.getLast()));
	}

	private static boolean areInRange(int first, int second) {
		var diff = Math.abs(first - second);
		return diff >= 1 && diff <= 3;
	}

	public static Report from(String line) {
		return new Report(
				Arrays.stream(line.split(" "))
						.map(Integer::parseInt)
						.toList());
	}
}
