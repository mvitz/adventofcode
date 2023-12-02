package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Day02.Game.Cubes;
import de.mvitz.aoc2023.Day02.Game.Rounds.Round;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

final class Day02 {

	private Day02() {
	}

	static long sumOfPossibleGameIDs(String input) {
		return input.lines()
				.map(Game::from)
				.filter(Game::isPossible)
				.mapToLong(Game::id)
				.sum();
	}

	static long sumOfMinimumCubesPower(String input) {
		return input.lines()
				.map(Game::from)
				.map(Game::minimalCubes)
				.mapToLong(Cubes::power)
				.sum();
	}

	record Game(long id, Cubes cubes, Rounds rounds) {

		boolean isPossible() {
			return rounds.eachContainsAtLeast(cubes);
		}

		Cubes minimalCubes() {
			return rounds.each()
					.map(Round::cubes)
					.reduce(Cubes::union)
					.orElseThrow();
		}

		static Game from(String input) {
			return new Game(
					Long.parseLong(input.substring(5, input.indexOf(':'))),
					Cubes.from("12 red, 13 green, 14 blue"),
					Rounds.from(input.substring(input.indexOf(':') + 2)));
		}

		record Cubes(EnumMap<Color, Long> value) {

			boolean isSubsetOf(Cubes cubes) {
				return Color.each()
						.allMatch(color -> countOfColored(color) <= cubes.countOfColored(color));
			}

			long countOfColored(Color color) {
				return value.getOrDefault(color, 0L);
			}

			Cubes union(Cubes cubes) {
				var newValue = new EnumMap<Color, Long>(Color.class);
				Color.each().forEach(color ->
						newValue.put(color, Math.max(countOfColored(color), cubes.countOfColored(color))));
				return new Cubes(newValue);
			}

			long power() {
				return value.values().stream()
						.reduce((a, b) -> a * b)
						.orElse(0L);
			}

			static Cubes from(String input) {
				var cubes = new EnumMap<Color, Long>(Color.class);
				Arrays.stream(input.strip().split(", "))
						.map(coloredCubes -> coloredCubes.split(" "))
						.forEach(numberColor -> cubes.put(Color.from(numberColor[1]), Long.parseLong(numberColor[0])));
				return new Cubes(cubes);
			}
		}

		record Rounds(List<Round> value) {

			boolean eachContainsAtLeast(Cubes cubes) {
				return value.stream()
						.allMatch(round -> round.containsLessThan(cubes));
			}

			Stream<Round> each() {
				return value.stream();
			}

			static Rounds from(String input) {
				return new Rounds(
						Arrays.stream(input.strip().split(";"))
								.map(Round::from)
								.toList());
			}

			record Round(Cubes cubes) {

				boolean containsLessThan(Cubes cubes) {
					return this.cubes.isSubsetOf(cubes);
				}

				static Round from(String input) {
					return new Round(Cubes.from(input));
				}
			}
		}

		enum Color {
			BLUE, RED, GREEN;

			static Stream<Color> each() {
				return Arrays.stream(values());
			}

			static Color from(String input) {
				return valueOf(input.strip().toUpperCase());
			}
		}
	}
}
