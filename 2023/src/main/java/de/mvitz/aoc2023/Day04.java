package de.mvitz.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.math.BigInteger.TWO;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

final class Day04 {

	private Day04() {
	}

	static long totalScratchcardPoints(String input) {
		return scratchcardsFrom(input).stream()
				.mapToLong(Scratchcard::points)
				.sum();
	}

	static long totalNumberOfScratchcards(String input) {
		var stack = new HashMap<Integer, Long>();
		for (var stackcard : scratchcardsFrom(input)) {
			var stackcardCount = stack.merge(stackcard.number, 1L, Long::sum);
			for (var i = 1; i <= stackcard.numberOfMatchingNumbers(); i++) {
				stack.merge(stackcard.number + i, stackcardCount, Long::sum);
			}
		}
		return stack.values().stream()
				.mapToLong(Long::longValue)
				.sum();
	}

	static List<Scratchcard> scratchcardsFrom(String input) {
		return input.lines()
				.map(Scratchcard::from)
				.toList();
	}

	record Scratchcard(int number, Set<Long> winningNumbers, Set<Long> numbers) {

		public long numberOfMatchingNumbers() {
			return winningNumbers.stream()
					.filter(numbers::contains)
					.count();
		}

		public long points() {
			var matching = numberOfMatchingNumbers();
			if (matching < 1) {
				return 0;
			}
			return TWO.pow((int) matching - 1).longValue();
		}

		public static Scratchcard from(String input) {
			var gameWithNumbers = input.strip().split(": ");
			var game = Integer.parseInt(gameWithNumbers[0].substring(5).strip());
			var numbers = gameWithNumbers[1].split(" \\| ");
			return new Scratchcard(
					game,
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
