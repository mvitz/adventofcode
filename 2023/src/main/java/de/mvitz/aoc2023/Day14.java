package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

final class Day14 {

	private Day14() {
	}

	static long totalLoadOnNorthSupportBeamsFor(String input) {
		return Platform.from(input)
				.tilt()
				.totalLoad();
	}

	record Point(int x, int y) {
	}

	sealed interface Rock {

		Point point();

		default long load() {
			return switch (this) {
				case RoundedRock _ -> point().y;
				case CubeShapedRock _ -> 0;
			};
		}

		record RoundedRock(Point point) implements Rock {
		}

		record CubeShapedRock(Point point) implements Rock {
		}

		static Optional<Rock> from(Point point, String tile) {
			return switch (tile) {
				case "O" -> Optional.of(new RoundedRock(point));
				case "#" -> Optional.of(new CubeShapedRock(point));
				default -> Optional.empty();
			};
		}
	}

	record Platform(int height, int width, List<Rock> rocks) {

		Platform tilt() {
			var rocks = new ArrayList<Rock>();

			for (var x = 0; x < width; x++) {
				var highestReachablePoint = height;
				for (var rock : rocksInColumn(x)) {
					if (rock instanceof Rock.RoundedRock) {
						rocks.add(new Rock.RoundedRock(new Point(x, highestReachablePoint)));
						highestReachablePoint--;
					} else {
						rocks.add(rock);
						highestReachablePoint = rock.point().y - 1;
					}
				}
			}

			return new Platform(height, width, rocks);
		}

		long totalLoad() {
			return rocks.stream()
					.mapToLong(Rock::load)
					.sum();
		}

		private List<Rock> rocksInColumn(int column) {
			return rocks.stream()
					.filter(rock -> rock.point().x == column)
					.sorted(Comparator.<Rock, Integer>comparing(rock -> rock.point().y).reversed())
					.toList();
		}

		static Platform from(String input) {
			var rocks = new ArrayList<Rock>();

			var lines = input.lines().toList();
			for (var y = 0; y < lines.size(); y++) {
				var columns = lines.get(y).split("");
				for (var x = 0; x < columns.length; x++) {
					Rock.from(new Point(x, lines.size() - y), columns[x])
							.ifPresent(rocks::add);
				}
			}

			return new Platform(
					lines.size(),
					lines.getFirst().length(),
					rocks);
		}
	}
}
