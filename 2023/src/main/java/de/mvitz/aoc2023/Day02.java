package de.mvitz.aoc2023;

import java.util.Arrays;
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

	record Game(long id, Cubes cubes, Rounds rounds) {

		boolean isPossible() {
			return rounds.everyContainsLessThan(cubes);
		}


		static Game from(String input) {
			return new Game(
					Long.parseLong(input.substring(5, input.indexOf(':'))),
					Cubes.from("12 red, 13 green, 14 blue"),
					Rounds.from(input.substring(input.indexOf(':') + 2)));
		}

		record Cubes(List<Cube> value) {

			boolean isSubsetOf(Cubes cubes) {
				return Arrays.stream(Color.values())
						.allMatch(color -> countOfColored(color) <= cubes.countOfColored(color));
			}

			long countOfColored(Color color) {
				return value.stream()
						.filter(cube -> cube.isColored(color))
						.count();
			}

			static Cubes from(String input) {
				return new Cubes(
						Arrays.stream(input.strip().split(", "))
								.map(coloredCubes -> coloredCubes.split(" "))
								.flatMap(numberColor -> Stream.generate(() -> Cube.from(numberColor[1])).limit(Long.parseLong(numberColor[0])))
								.toList());
			}
		}

		record Rounds(List<Round> value) {

			boolean everyContainsLessThan(Cubes cubes) {
				return value.stream()
						.allMatch(round -> round.containsLessThan(cubes));
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

		record Cube(Color color) {

			boolean isColored(Color color) {
				return this.color == color;
			}

			static Cube from(String input) {
				return new Cube(Color.from(input));
			}
		}

		enum Color {
			BLUE, RED, GREEN;

			static Color from(String input) {
				return valueOf(input.strip().toUpperCase());
			}
		}
	}
}
