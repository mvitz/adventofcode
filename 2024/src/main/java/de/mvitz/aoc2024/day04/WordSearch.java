package de.mvitz.aoc2024.day04;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

final class WordSearch {

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

	public String wordAt(Point start, Direction direction, int length) {
		List<Optional<String>> values = new ArrayList<>();
		var coordinate = start;
		for (int step = 0; step < length; step++) {
			values.add(valueAt(coordinate));
			coordinate = direction.from(coordinate);
		}
		return values.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(joining());
	}

	public Optional<String> valueAt(Point coordinate) {
		if (coordinate.y() < 0 || coordinate.y() >= fields.length) {
			return Optional.empty();
		}
		var row = fields[coordinate.y()];

		if (coordinate.x() < 0 || coordinate.x() >= row.length) {
			return Optional.empty();
		}
		return Optional.of(row[coordinate.x()]);
	}

	public static WordSearch from(String input) {
		return new WordSearch(
				input.lines()
						.map(line -> line.split(""))
						.toArray(String[][]::new));
	}
}
