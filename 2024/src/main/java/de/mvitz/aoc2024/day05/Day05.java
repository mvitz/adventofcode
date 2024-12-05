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
		var rulesAndUpdates = input.split("\n\n");
		var rules = rulesFrom(rulesAndUpdates[0]);

		return rulesAndUpdates[1].lines()
				.map(Day05::updateFrom)
				.filter(update -> isInRightOrder(update, rules))
				.mapToInt(Day05::middleOf)
				.sum();
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
