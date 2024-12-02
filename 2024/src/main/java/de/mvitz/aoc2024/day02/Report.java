package de.mvitz.aoc2024.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

import static java.util.Comparator.reverseOrder;

public final class Report {

	private final List<Integer> levels;

	private Report(List<Integer> levels) {
		this.levels = levels;
	}

	public boolean isSafe() {
		return areSafe(levels);

	}

	public boolean isToleratedSafe() {
		if (isSafe()) {
			return true;
		}

		return IntStream.range(0, levels.size())
				.mapToObj(index -> without(levels, index))
				.anyMatch(Report::areSafe);
	}

	@SuppressWarnings("preview")
	private static boolean areSafe(List<Integer> levels) {
		return isSorted(levels) && levels.stream()
				.gather(Gatherers.windowSliding(2))
				.allMatch(pair -> areInRange(pair.getFirst(), pair.getLast()));
	}

	private static boolean areInRange(int first, int second) {
		var diff = Math.abs(first - second);
		return diff >= 1 && diff <= 3;
	}

	private static boolean isSorted(List<Integer> list) {
		return list.equals(list.stream().sorted().toList())
			   || list.equals(list.stream().sorted(reverseOrder()).toList());
	}

	private static List<Integer> without(List<Integer> list, int indexToDrop) {
		var result = new ArrayList<>(list);
		result.remove(indexToDrop);
		return result;
	}

	public static Report from(String line) {
		return new Report(
				Arrays.stream(line.split(" "))
						.map(Integer::parseInt)
						.toList());
	}
}
