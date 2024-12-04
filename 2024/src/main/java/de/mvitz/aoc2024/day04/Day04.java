package de.mvitz.aoc2024.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public final class Day04 {

	private Day04() {
	}

	public static long findNumberOfXmasAppearencesIn(String input) {
		var wordSearch = WordSearch.from(input);
		return wordSearch.coordinatesWith("X").stream()
				.mapToLong(x -> Arrays.stream(WordSearch.Direction.values())
						.filter(direction -> wordSearch.spells("XMAS", x, direction))
						.count())
				.sum();
	}

	private static final class WordSearch {

		private final String[][] fields;

		private WordSearch(String[][] fields) {
			this.fields = fields;
		}

		public List<Point> coordinatesWith(String value) {
			var coordinates = new ArrayList<Point>();
			for (int y = 0; y < fields.length; y++) {
				var row = fields[y];
				for (int x = 0; x < row.length; x++) {
					var v = row[x];
					if (v.equals(value)) {
						coordinates.add(new Point(x, y));
					}
				}
			}
			return coordinates;
		}

		public boolean spells(String value, Point start, Direction direction) {
			return value.equals(wordAt(start, direction, value.length()));
		}

		public String wordAt(Point start, Direction direction, int length) {
			List<Optional<String>> values = new ArrayList<>();
			var coordinate = start;
			for (int step = 0; step < length; step++) {
				values.add(valueAt(coordinate));
				coordinate = direction.apply(coordinate);
			}
			return values.stream()
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(joining());
		}

		public Optional<String> valueAt(Point coordinate) {
			if (coordinate.y < 0 || coordinate.y >= fields.length) {
				return Optional.empty();
			}
			var row = fields[coordinate.y];

			if (coordinate.x < 0 || coordinate.x >= row.length) {
				return Optional.empty();
			}
			return Optional.of(row[coordinate.x]);
		}

		public static WordSearch from(String input) {
			return new WordSearch(
					input.lines()
							.map(line -> line.split(""))
							.toArray(String[][]::new));
		}

		private record Point(int x, int y) {
		}

		private enum Direction {
			UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;

			public Point apply(Point point) {
				return switch (this) {
					case UP -> new Point(point.x, point.y - 1);
					case UP_RIGHT -> new Point(point.x + 1, point.y - 1);
					case RIGHT -> new Point(point.x + 1, point.y);
					case DOWN_RIGHT -> new Point(point.x + 1, point.y + 1);
					case DOWN -> new Point(point.x, point.y + 1);
					case DOWN_LEFT -> new Point(point.x - 1, point.y + 1);
					case LEFT -> new Point(point.x - 1, point.y);
					case UP_LEFT -> new Point(point.x - 1, point.y - 1);
				};
			}
		}
	}
}
