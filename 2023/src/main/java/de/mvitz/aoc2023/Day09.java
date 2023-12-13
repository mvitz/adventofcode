package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Gatherers;

final class Day09 {

	private Day09() {
	}

	static long sumOfForwardExtrapolatedValues(String input) {
		return sumOfExtrapolatedValues(input, Day09::forwardExtrapolationFor);
	}

	static long sumOfBackwardsExtrapolatedValues(String input) {
		return sumOfExtrapolatedValues(input, Day09::backwardsExtrapolationFor);
	}

	private static long sumOfExtrapolatedValues(String input, ToLongFunction<List<Long>> extrapolation) {
		return input.lines()
				.map(Day09::parseHistory)
				.mapToLong(extrapolation)
				.sum();
	}

	static List<Long> parseHistory(String line) {
		return Arrays.stream(line.split(" "))
				.map(Long::parseLong)
				.toList();
	}

	static long forwardExtrapolationFor(List<Long> history) {
		return extrapolationFor(history, List::getLast, Long::sum);
	}

	static List<Long> differencesBetweenValues(List<Long> values) {
		return values.stream()
				.gather(Gatherers.windowSliding(2))
				.map(window -> window.getLast() - window.getFirst())
				.toList();
	}

	static long backwardsExtrapolationFor(List<Long> history) {
		return extrapolationFor(history, List::getFirst, (first, second) -> second - first);
	}

	private static long extrapolationFor(List<Long> history, Function<List<Long>, Long> extraction, BinaryOperator<Long> filling) {
		var sequences = new ArrayList<List<Long>>();
		sequences.add(history);

		while (!sequences.getLast().stream().allMatch(value -> value == 0)) {
			sequences.add(differencesBetweenValues(sequences.getLast()));
		}

		return sequences.reversed().stream()
				.map(extraction)
				.reduce(0L, filling);
	}
}
