package de.mvitz.aoc2024.day04;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Pair;
import de.mvitz.aoc2024.utils.Point;

import java.util.*;

import static de.mvitz.aoc2024.utils.Gatherers.mapWithIndex;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

final class WordSearch {

	private final Map<Point, String> fields;

	private WordSearch(Map<Point, String> fields) {
		this.fields = fields;
	}

	public List<Point> coordinatesWith(String value) {
		return fields.entrySet().stream()
				.filter(field -> field.getValue().equals(value))
				.map(Map.Entry::getKey)
				.toList();
	}

	public String wordAt(Point start, Direction direction, int length) {
		var values = new ArrayList<Optional<String>>();

		var coordinate = start;
		for (var step = 0; step < length; step++) {
			values.add(valueAt(coordinate));
			coordinate = direction.from(coordinate);
		}

		return values.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(joining());
	}

	public Optional<String> valueAt(Point coordinate) {
		return Optional.ofNullable(fields.get(coordinate));
	}

	@SuppressWarnings("preview")
	public static WordSearch from(String input) {
		return new WordSearch(input.lines()
				.gather(mapWithIndex((y, line) ->
						Arrays.stream(line.split(""))
								.gather(mapWithIndex((x, value) ->
										Pair.of(new Point(x, y), value)))
				))
				.flatMap(identity())
				.collect(toMap(Pair::left, Pair::right)));
	}
}
