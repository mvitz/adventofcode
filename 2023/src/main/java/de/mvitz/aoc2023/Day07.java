package de.mvitz.aoc2023;

import java.util.*;
import java.util.stream.Stream;

final class Day07 {

	private Day07() {
	}

	static long totalWinningsOf(String input) {
		return winningsOf(parse(input)
				.sorted(Comparator.comparing(HandWithBid::hand, Hand.naturalOrder()))
				.map(HandWithBid::bid)
				.toList());
	}

	static long totalWinningsWithJokersOf(String input) {
		return winningsOf(parse(input)
				.sorted(Comparator.comparing(HandWithBid::hand, Hand.withJoker(Hand.Card._J)))
				.map(HandWithBid::bid)
				.toList());
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

	private static long winningsOf(List<Long> bidsSortedByRank) {
		var winnings = 0L;
		for (var i = 0; i < bidsSortedByRank.size(); i++) {
			winnings += bidsSortedByRank.get(i) * (i + 1);
		}
		return winnings;
	}

	record HandWithBid(Hand hand, Long bid) {
	}

	public record Hand(List<Card> cards) {

		public Type strength() {
			return strength(null);
		}

		public Type strength(Card joker) {
			return Type.of(this, joker);
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

		public static Comparator<Hand> naturalOrder() {
			return Comparator.<Hand, Type>comparing(Hand::strength)
					.thenComparing(hand -> hand.card(0), Card.naturalOrder())
					.thenComparing(hand -> hand.card(1), Card.naturalOrder())
					.thenComparing(hand -> hand.card(2), Card.naturalOrder())
					.thenComparing(hand -> hand.card(3), Card.naturalOrder())
					.thenComparing(hand -> hand.card(4), Card.naturalOrder());
		}

		public static Comparator<Hand> withJoker(Card joker) {
			return Comparator.<Hand, Type>comparing(hand -> hand.strength(joker))
					.thenComparing(hand -> hand.card(0), Card.withJoker(joker))
					.thenComparing(hand -> hand.card(1), Card.withJoker(joker))
					.thenComparing(hand -> hand.card(2), Card.withJoker(joker))
					.thenComparing(hand -> hand.card(3), Card.withJoker(joker))
					.thenComparing(hand -> hand.card(4), Card.withJoker(joker));
		}

		public enum Card {
			_2, _3, _4, _5, _6, _7, _8, _9, _T, _J, _Q, _K, _A;

			public static Card from(String card) {
				return Card.valueOf(STR."_\{card}");
			}

			public int countIn(List<Card> cards) {
				return (int) cards.stream().filter(this::equals).count();
			}

			public static Comparator<Card> naturalOrder() {
				return Comparator.naturalOrder();
			}

			public static Comparator<Card> withJoker(Card joker) {
				return (first, second) -> {
					if (first == joker && second == joker) {
						return 0;
					}
					if (first == joker) {
						return -1;
					}
					if (second == joker) {
						return 1;
					}
					return first.compareTo(second);
				};
			}
		}

		public enum Type {
			HIGH_CARD {
				@Override
				public Type next(Card current, List<Card> cards) {
					return current.countIn(cards) == 2 ? ONE_PAIR : HIGH_CARD;
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return switch (joker.countIn(cards)) {
						case 0 -> HIGH_CARD;
						case 1 -> ONE_PAIR;
						case 2 -> THREE_OF_A_KIND;
						case 3 -> FOUR_OF_A_KIND;
						default -> FIVE_OF_A_KIND;
					};
				}
			},
			ONE_PAIR {
				@Override
				public Type next(Card current, List<Card> cards) {
					return switch (current.countIn(cards)) {
						case 1 -> ONE_PAIR;
						case 2 -> TWO_PAIR;
						default -> THREE_OF_A_KIND;
					};
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return switch (joker.countIn(cards)) {
						case 0 -> ONE_PAIR;
						case 1 -> THREE_OF_A_KIND;
						case 2 -> FOUR_OF_A_KIND;
						default -> FIVE_OF_A_KIND;
					};
				}
			},
			TWO_PAIR {
				@Override
				public Type next(Card current, List<Card> cards) {
					return current.countIn(cards) == 1 ? TWO_PAIR : FULL_HOUSE;
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return switch (joker.countIn(cards)) {
						case 0 -> TWO_PAIR;
						case 1 -> FULL_HOUSE;
						case 2 -> FOUR_OF_A_KIND;
						default -> FIVE_OF_A_KIND;
					};
				}
			},
			THREE_OF_A_KIND {
				@Override
				public Type next(Card current, List<Card> cards) {
					return switch (current.countIn(cards)) {
						case 1 -> THREE_OF_A_KIND;
						case 2 -> FULL_HOUSE;
						default -> FOUR_OF_A_KIND;
					};
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return switch (joker.countIn(cards)) {
						case 0 -> THREE_OF_A_KIND;
						case 1 -> FOUR_OF_A_KIND;
						default -> FIVE_OF_A_KIND;
					};
				}
			},
			FULL_HOUSE {
				@Override
				public Type next(Card current, List<Card> cards) {
					return FULL_HOUSE;
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return FULL_HOUSE;
				}
			},
			FOUR_OF_A_KIND {
				@Override
				public Type next(Card current, List<Card> cards) {
					return current.countIn(cards) == 1 ? FOUR_OF_A_KIND : FIVE_OF_A_KIND;
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return joker.countIn(cards) == 0 ? FOUR_OF_A_KIND : FIVE_OF_A_KIND;
				}
			},
			FIVE_OF_A_KIND {
				@Override
				public Type next(Card current, List<Card> cards) {
					return FIVE_OF_A_KIND;
				}

				@Override
				public Type joker(Card joker, List<Card> cards) {
					return FIVE_OF_A_KIND;
				}
			};

			public abstract Type next(Card current, List<Card> cards);

			public abstract Type joker(Card joker, List<Card> cards);

			public static Type of(Hand hand, Card joker) {
				return process(HIGH_CARD, hand.cards.iterator(), new ArrayList<>(), joker);
			}

			private static Type process(Type type, Iterator<Card> cards, List<Card> processedCards, Card joker) {
				if (!cards.hasNext()) {
					if (joker == null) {
						return type;
					}
					return type.joker(joker, processedCards);
				}

				var card = cards.next();
				processedCards.add(card);

				if (card == joker) {
					return process(type, cards, processedCards, joker);
				}

				return process(type.next(card, processedCards), cards, processedCards, joker);
			}
		}
	}
}
