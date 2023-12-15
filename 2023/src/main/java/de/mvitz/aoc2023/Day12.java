package de.mvitz.aoc2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

final class Day12 {

	private Day12() {
	}

	static long sumOfArrangementsFor(String input) {
		return input.lines()
				.map(ConditionRecord::from)
				.map(ConditionRecord::arrangements)
				.mapToLong(List::size)
				.sum();
	}

	public record ConditionRecord(String springs, List<Integer> checksum) {

		public List<ConditionRecord> arrangements() {
			if (!isDamaged()) {
				if (isValid()) {
					return List.of(this);
				} else {
					return List.of();
				}
			}

			var arrangements = List.of(this);
			while (arrangements.getFirst().isDamaged()) {
				arrangements = arrangements.stream()
						.flatMap(a -> Stream.of(
								new ConditionRecord(a.springs.replaceFirst("\\?", "."), a.checksum),
								new ConditionRecord(a.springs.replaceFirst("\\?", "#"), a.checksum)))
						.toList();
			}
			return arrangements.parallelStream()
					.filter(ConditionRecord::isValid)
					.toList();
		}

		public boolean isDamaged() {
			return springs.contains("?");
		}

		public boolean isValid() {
			return checksum.equals(Arrays.stream(springs.split("\\.+"))
					.filter(not(String::isBlank))
					.map(String::length)
					.toList());
		}

		public static ConditionRecord from(String input) {
			var parts = input.split(" ");
			return new ConditionRecord(
					parts[0],
					Arrays.stream(parts[1].split(","))
							.map(Integer::parseInt)
							.toList());
		}
	}

	private static long count(char ch, String str) {
		return str.chars().filter(c -> c == ch).count();
	}
}
