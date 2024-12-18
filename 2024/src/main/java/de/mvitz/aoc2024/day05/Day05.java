package de.mvitz.aoc2024.day05;

import de.mvitz.aoc2024.utils.Pair;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toMap;

public final class Day05 {

	private Day05() {
	}

	public static long sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates(String input) {
		var rulesAndUpdates = rulesAndUpdatesFrom(input);
		return rulesAndUpdates.right().stream()
				.filter(update -> isInRightOrder(update, rulesAndUpdates.left()))
				.mapToInt(Day05::middleOf)
				.sum();
	}

	public static long sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering(String input) {
		var rulesAndUpdates = rulesAndUpdatesFrom(input);
		return rulesAndUpdates.right().stream()
				.filter(update -> !isInRightOrder(update, rulesAndUpdates.left()))
				.map(update -> update.stream().sorted(rulesAndUpdates.left()).toList())
				.mapToInt(Day05::middleOf)
				.sum();
	}

	private static Pair<Comparator<Integer>, List<List<Integer>>> rulesAndUpdatesFrom(String input) {
		var rulesAndUpdates = input.split("\n\n");
		return Pair.of(
				rulesFrom(rulesAndUpdates[0]),
				updatesFrom(rulesAndUpdates[1]));
	}

	private static Comparator<Integer> rulesFrom(String input) {
		var numberToBefore = input.lines()
				.map(Day05::ruleFrom)
				.collect(toMap(Pair::left, rule -> Set.of(rule.right()), Day05::merge));
		return (Integer first, Integer second) -> {
			if (first.equals(second)) {
				return 0;
			}
			if (numberToBefore.getOrDefault(first, emptySet()).contains(second)) {
				return -1;
			}
			return 1;
		};
	}

	private static Pair<Integer, Integer> ruleFrom(String line) {
		var parts = line.split("\\|");
		return Pair.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	private static List<List<Integer>> updatesFrom(String input) {
		return input.lines()
				.map(Day05::updateFrom)
				.toList();
	}

	private static List<Integer> updateFrom(String line) {
		return Arrays.stream(line.split(","))
				.map(Integer::parseInt)
				.toList();
	}

	private static boolean isInRightOrder(List<Integer> update, Comparator<Integer> rules) {
		var sorted = new TreeSet<>(rules);
		sorted.addAll(update);

		return update.equals(new ArrayList<>(sorted));
	}

	private static <T> T middleOf(List<T> list) {
		return list.get(list.size() / 2);
	}

	private static <T> Set<T> merge(Set<T> first, Set<T> second) {
		var merged = new HashSet<T>();
		merged.addAll(first);
		merged.addAll(second);
		return unmodifiableSet(merged);
	}
}
