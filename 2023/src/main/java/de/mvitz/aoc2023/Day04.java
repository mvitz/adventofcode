package de.mvitz.aoc2023;

import java.util.Arrays;
import java.util.Set;

import static java.math.BigInteger.TWO;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

final class Day04 {

	private Day04() {
	}

	static long totalScratchcardPoints(String input) {
		return input.lines()
				.map(Scratchcard::from)
				.mapToLong(Scratchcard::points)
				.sum();
	}

	record Scratchcard(Set<Long> winningNumbers, Set<Long> numbers) {

		public long points() {
			var matching = winningNumbers.stream()
					.filter(numbers::contains)
					.count();
			if (matching < 1) {
				return 0;
			}
			return TWO.pow((int) matching - 1).longValue();
		}

		public static Scratchcard from(String input) {
			var numbers = input.strip().split(": ")[1].split(" \\| ");
			return new Scratchcard(
					parseNumbers(numbers[0]),
					parseNumbers(numbers[1]));
		}

		private static Set<Long> parseNumbers(String input) {
			return Arrays.stream(input.strip().split(" "))
					.map(String::strip)
					.filter(not(String::isBlank))
					.map(Long::parseLong)
					.collect(toSet());
		}
	}
}
