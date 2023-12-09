package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

final class Day07 {

	private Day07() {
	}

	static long totalWinningsOf(String input) {
		var bidsSortedByRank = parse(input)
				.sorted(Comparator.reverseOrder())
				.map(HandWithBid::bid)
				.toList();
		var winnings = 0L;
		for (var i = 0; i < bidsSortedByRank.size(); i++) {
			winnings += bidsSortedByRank.get(i) * (i + 1);
		}
		return winnings;
	}

	private static Stream<HandWithBid> parse(String input) {
		return input.lines()
				.map(Day07::parseLine);
	}

	private static HandWithBid parseLine(String line) {
		var parts = line.split(" ");
		return new HandWithBid(
				Hand.from(parts[0]),
				Long.parseLong(parts[1]));
	}

	record HandWithBid(Hand hand, Long bid) implements Comparable<HandWithBid> {

		@Override
		public int compareTo(HandWithBid o) {
			return Comparator.comparing(HandWithBid::hand).compare(this, o);
		}
	}

	record Hand(List<Card> cards) implements Comparable<Hand> {

		public Type strength() {
			return Type.from(this);
		}

		public Card card(int index) {
			return cards.get(index);
		}

		public static Hand from(String input) {
			return new Hand(
					Arrays.stream(input.split(""))
							.map(Card::from)
							.toList());
		}

		@Override
		public int compareTo(Hand o) {
			return Comparator.comparing(Hand::strength)
					.thenComparing(hand -> hand.card(0))
					.thenComparing(hand -> hand.card(1))
					.thenComparing(hand -> hand.card(2))
					.thenComparing(hand -> hand.card(3))
					.thenComparing(hand -> hand.card(4))
					.compare(this, o);
		}

		enum Card {
			_A, _K, _Q, _J, _T, _9, _8, _7, _6, _5, _4, _3, _2;

			static Card from(String card) {
				return Card.valueOf(STR."_\{card}");
			}
		}

		enum Type {
			FIVE_OF_A_KIND,
			FOUR_OF_A_KIND,
			FULL_HOUSE,
			THREE_OF_A_KIND,
			TWO_PAIR,
			ONE_PAIR,
			HIGH_CARD;

			static Type from(Hand hand) {
				var type = HIGH_CARD;
				var processedCards = new ArrayList<Card>();
				for (var card : hand.cards) {
					type = process(type, card, processedCards);
					processedCards.add(card);
				}
				return type;
			}

			static Type process(Type type, Card card, List<Card> processedCards) {
				if (!processedCards.contains(card)) {
					return type;
				}
				var numberOfCard = processedCards.stream().filter(card::equals).count();

				return switch (type) {
					case HIGH_CARD -> ONE_PAIR;
					case ONE_PAIR ->
							numberOfCard == 1 ? TWO_PAIR : THREE_OF_A_KIND;
					case TWO_PAIR -> FULL_HOUSE;
					case THREE_OF_A_KIND ->
							numberOfCard == 3 ? FOUR_OF_A_KIND : FULL_HOUSE;
					case FOUR_OF_A_KIND -> FIVE_OF_A_KIND;
					default -> type;
				};
			}
		}
	}
}
