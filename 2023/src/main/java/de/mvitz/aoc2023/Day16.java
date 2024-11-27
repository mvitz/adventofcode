
package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Utils.Pair;
import de.mvitz.aoc2023.Utils.Point;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.mvitz.aoc2023.Day16.Contraption.Direction.*;
import static java.util.stream.Collectors.toSet;

final class Day16 {

	private Day16() {
	}

	static int numberOfEnergizedTilesFor(String input) {
		return Contraption.parse(input)
				.beam(new Point(0, 0), RIGHT)
				.size();
	}

	static int maximumNumberOfEnergizedTilesFor(String input) {
		var contraption = Contraption.parse(input);

		return Stream.concat(
						IntStream.range(0, contraption.width)
								.boxed()
								.flatMap(x -> Stream.of(Pair.of(new Point(x, 0), DOWN), Pair.of(new Point(x, contraption.height - 1), UP))),
						IntStream.range(0, contraption.height)
								.boxed()
								.flatMap(y -> Stream.of(Pair.of(new Point(0, y), RIGHT), Pair.of(new Point(contraption.width - 1, y), LEFT))))
				.parallel()
				.mapToInt(start -> contraption.beam(start.first(), start.second()).size())
				.max().orElseThrow();

	}

	record Contraption(int height, int width, List<Tile> tiles) {

		public Set<Point> beam(Point start, Direction direction) {
			var seen = new HashSet<Pair<Point, Direction>>();

			var queue = new ArrayDeque<Pair<Point, Direction>>();
			queue.add(Pair.of(start, direction));

			while (!queue.isEmpty()) {
				var next = queue.pop();
				if (!seen.contains(next)) {
					tileAt(next.first()).ifPresent(tile -> {
						seen.add(next);
						tile.directionsFrom(next.second())
								.forEach(dir -> queue.add(Pair.of(dir.next(tile.point()), dir)));
					});
				}
			}

			return seen.stream()
					.map(Pair::first)
					.collect(toSet());
		}

		private Optional<Tile> tileAt(Point point) {
			return tiles.stream()
					.filter(tile -> tile.point().equals(point))
					.findAny();
		}

		public static Contraption parse(String input) {
			var tiles = new ArrayList<Tile>();

			var lines = input.lines().toList();
			for (var y = 0; y < lines.size(); y++) {
				var columns = lines.get(y).split("");
				for (var x = 0; x < columns.length; x++) {
					tiles.add(Tile.parse(new Point(x, y), columns[x]));
				}
			}

			return new Contraption(
					lines.size(),
					lines.getFirst().split("").length,
					tiles);
		}

		enum Direction {
			UP, RIGHT, DOWN, LEFT;

			Point next(Point from) {
				return switch (this) {
					case UP -> from.above();
					case RIGHT -> from.right();
					case DOWN -> from.below();
					case LEFT -> from.left();
				};
			}
		}

		sealed interface Tile {

			Point point();

			default Set<Contraption.Direction> directionsFrom(Direction current) {
				return switch (this) {
					case EmptySpace _ -> Set.of(current);
					case UpwardMirror _ -> switch (current) {
						case UP -> Set.of(RIGHT);
						case RIGHT -> Set.of(UP);
						case DOWN -> Set.of(LEFT);
						case LEFT -> Set.of(DOWN);
					};
					case DownwardMirror _ -> switch (current) {
						case UP -> Set.of(LEFT);
						case RIGHT -> Set.of(DOWN);
						case DOWN -> Set.of(RIGHT);
						case LEFT -> Set.of(UP);
					};
					case VerticalSplitter _ -> switch (current) {
						case UP -> Set.of(UP);
						case RIGHT, LEFT -> Set.of(DOWN, UP);
						case DOWN -> Set.of(DOWN);
					};
					case HorizontalSplitter _ -> switch (current) {
						case UP, DOWN -> Set.of(LEFT, RIGHT);
						case RIGHT -> Set.of(RIGHT);
						case LEFT -> Set.of(LEFT);
					};
				};
			}

			static Tile parse(Point point, String type) {
				return switch (type) {
					case EmptySpace.TYPE -> new EmptySpace(point);
					case UpwardMirror.TYPE -> new UpwardMirror(point);
					case DownwardMirror.TYPE -> new DownwardMirror(point);
					case VerticalSplitter.TYPE -> new VerticalSplitter(point);
					case HorizontalSplitter.TYPE -> new HorizontalSplitter(point);
					default -> throw new IllegalArgumentException("Found unknown type '" + type + "' at point: " + point);
				};
			}

			record EmptySpace(Point point) implements Tile {

				static final String TYPE = ".";
			}

			record UpwardMirror(Point point) implements Tile {

				static final String TYPE = "/";
			}

			record DownwardMirror(Point point) implements Tile {

				static final String TYPE = "\\";
			}

			record VerticalSplitter(Point point) implements Tile {

				static final String TYPE = "|";
			}

			record HorizontalSplitter(Point point) implements Tile {

				static final String TYPE = "-";
			}
		}
	}
}
