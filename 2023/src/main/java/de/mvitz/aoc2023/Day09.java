package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Day09 {

	private Day09() {
	}

	static long sumOfExtrapolatedValues(String input) {
		return input.lines()
				.map(Day09::parseHistory)
				.mapToLong(Day09::extrapolationFor)
				.sum();
	}

	static List<Long> parseHistory(String line) {
		return Arrays.stream(line.split(" "))
				.map(Long::parseLong)
				.toList();
	}

	static long extrapolationFor(List<Long> history) {
		var sequences = new ArrayList<List<Long>>();
		sequences.add(history);

		while (!sequences.getLast().stream().allMatch(value -> value == 0)) {
			sequences.add(differencesBetweenValues(sequences.getLast()));
		}

		return sequences.reversed().stream()
				.map(List::getLast)
				.reduce(0L, Long::sum);
	}

	static List<Long> differencesBetweenValues(List<Long> values) {
		var differences = new ArrayList<Long>();
		for (var i = 0; i < values.size() - 1; i++) {
			differences.add(values.get(i + 1) - values.get(i));
		}
		return differences;
	}
}
