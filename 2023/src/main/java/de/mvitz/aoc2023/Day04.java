package de.mvitz.aoc2023;

import java.util.*;

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
		var pile = new ScratchcardPile();

		scratchcardsFrom(input).forEach(card -> {
			var scratchcardCount = pile.put(card.number);
			for (var i = 1; i <= card.numberOfMatchingNumbers(); i++) {
				pile.put(card.number + i, scratchcardCount);
			}
		});

		return pile.numberOfCards();
	}

	static List<Scratchcard> scratchcardsFrom(String input) {
		return input.lines()
				.map(Scratchcard::from)
				.toList();
	}

	private static class ScratchcardPile {

		private final Map<Integer, Long> cards = new HashMap<>();

		public long put(int cardNumber) {
			return put(cardNumber, 1);
		}

		public long put(int cardNumber, long times) {
			return cards.merge(cardNumber, times, Long::sum);
		}

		public long numberOfCards() {
			return cards.values().stream()
					.mapToLong(Long::longValue)
					.sum();
		}
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
