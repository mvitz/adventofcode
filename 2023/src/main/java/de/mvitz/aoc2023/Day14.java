package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Utils.Point;

import java.util.*;

import static de.mvitz.aoc2023.Day14.Platform.Direction.*;

final class Day14 {

	private Day14() {
	}

	static long totalLoadOnNorthSupportBeamsFor(String input) {
		return Platform.from(input)
				.tilt(NORTH)
				.totalLoad();
	}

	static long totalLoadOnNorthSupportBeamsAfterCyclesFor(String input) {
		return Platform.from(input)
				.spin(1_000_000_000)
				.totalLoad();
	}

	sealed interface Rock {

		Point point();

		default long load() {
			return switch (this) {
				case RoundedRock _ -> point().y() + 1;
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

	record Platform(int height, int width, Set<Rock> rocks) {

		Platform tilt(Direction direction) {
			return direction.tilt(this);
		}

		Platform spin() {
			return tilt(NORTH).tilt(WEST).tilt(SOUTH).tilt(EAST);
		}

		Platform spin(int times) {
			var cache = new HashMap<Platform, Integer>();
			var platform = this;

			for (int i = 0; i < times; i++) {
				var spun = platform.spin();
				if (platform.equals(spun)) {
					return platform;
				}

				platform = spun;

				if (cache.containsKey(platform)) {
					var j = cache.get(platform);

					var skip = i - j;
					if (i + skip < times) {
						i += skip;
						cache.put(platform, i);
						continue;
					}
				}

				cache.put(platform, i);
			}
			return platform;
		}

		long totalLoad() {
			return rocks.stream()
					.mapToLong(Rock::load)
					.sum();
		}

		private List<Rock> rocksInColumn(int column) {
			return rocks.stream()
					.filter(rock -> rock.point().isInColumn(column))
					.sorted(Comparator.<Rock, Integer>comparing(rock -> rock.point().y()).reversed())
					.toList();
		}

		private List<Rock> rocksInRow(int row) {
			return rocks.stream()
					.filter(rock -> rock.point().isInRow(row))
					.sorted(Comparator.<Rock, Integer>comparing(rock -> rock.point().x()).reversed())
					.toList();
		}

		@Override
		public String toString() {
			var rows = new ArrayList<String>();
			for (var y = 0; y < height; y++) {
				var row = new ArrayList<String>();
				for (var x = 0; x < width; x++) {
					var point = new Point(x, y);
					row.add(rocks.stream()
							.filter(rock -> rock.point().equals(point))
							.findAny()
							.map(rock -> switch (rock) {
								case Rock.RoundedRock _ -> "O";
								case Rock.CubeShapedRock _ -> "#";
							})
							.orElse("."));
				}
				rows.add(String.join("", row));
			}
			return String.join("\n", rows.reversed());
		}

		static Platform from(String input) {
			var rocks = new HashSet<Rock>();

			var lines = input.lines().toList();
			for (var y = 0; y < lines.size(); y++) {
				var columns = lines.get(y).split("");
				for (var x = 0; x < columns.length; x++) {
					Rock.from(new Point(x, lines.size() - 1 - y), columns[x])
							.ifPresent(rocks::add);
				}
			}

			return new Platform(
					lines.size(),
					lines.getFirst().length(),
					rocks);
		}

		enum Direction {
			NORTH {
				@Override
				Platform tilt(Platform platform) {
					var rocks = new HashSet<Rock>();

					for (var x = 0; x < platform.width; x++) {
						var highestReachablePoint = platform.height - 1;
						for (var rock : platform.rocksInColumn(x)) {
							if (rock instanceof Rock.RoundedRock) {
								rocks.add(new Rock.RoundedRock(new Point(x, highestReachablePoint)));
								highestReachablePoint--;
							} else {
								rocks.add(rock);
								highestReachablePoint = rock.point().y() - 1;
							}
						}
					}

					return new Platform(platform.height, platform.width, rocks);
				}
			},
			WEST {
				@Override
				Platform tilt(Platform platform) {
					var rocks = new HashSet<Rock>();

					for (var y = 0; y < platform.height; y++) {
						var mostLeftReachablePoint = 0;
						for (var rock : platform.rocksInRow(y).reversed()) {
							if (rock instanceof Rock.RoundedRock) {
								rocks.add(new Rock.RoundedRock(new Point(mostLeftReachablePoint, y)));
								mostLeftReachablePoint++;
							} else {
								rocks.add(rock);
								mostLeftReachablePoint = rock.point().x() + 1;
							}
						}
					}

					return new Platform(platform.height, platform.width, rocks);
				}
			},
			SOUTH {
				@Override
				Platform tilt(Platform platform) {
					var rocks = new HashSet<Rock>();

					for (var x = 0; x < platform.width; x++) {
						var lowestReachablePoint = 0;
						for (var rock : platform.rocksInColumn(x).reversed()) {
							if (rock instanceof Rock.RoundedRock) {
								rocks.add(new Rock.RoundedRock(new Point(x, lowestReachablePoint)));
								lowestReachablePoint++;
							} else {
								rocks.add(rock);
								lowestReachablePoint = rock.point().y() + 1;
							}
						}
					}

					return new Platform(platform.height, platform.width, rocks);
				}
			},
			EAST {
				@Override
				Platform tilt(Platform platform) {
					var rocks = new HashSet<Rock>();

					for (var y = 0; y < platform.height; y++) {
						var mostRightReachablePoint = platform.width - 1;
						for (var rock : platform.rocksInRow(y)) {
							if (rock instanceof Rock.RoundedRock) {
								rocks.add(new Rock.RoundedRock(new Point(mostRightReachablePoint, y)));
								mostRightReachablePoint--;
							} else {
								rocks.add(rock);
								mostRightReachablePoint = rock.point().x() - 1;
							}
						}
					}

					return new Platform(platform.height, platform.width, rocks);
				}
			};

			abstract Platform tilt(Platform platform);
		}
	}
}
