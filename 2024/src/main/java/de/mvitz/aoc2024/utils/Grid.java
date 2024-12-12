package de.mvitz.aoc2024.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.mvitz.aoc2024.utils.Gatherers.mapWithIndex;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class Grid {

	private final Map<Point, String> fields;

	public Grid(Map<Point, String> fields) {
		this.fields = fields;
	}

	public Optional<String> valueAt(Point coordinate) {
		return Optional.ofNullable(fields.get(coordinate));
	}

	public List<Point> coordinatesWith(String value) {
		return fields.entrySet().stream()
				.filter(field -> field.getValue().equals(value))
				.map(Map.Entry::getKey)
				.toList();
	}

	@SuppressWarnings("preview")
	public static Grid from(String input) {
		return new Grid(input.lines()
				.gather(mapWithIndex((y, line) ->
						Arrays.stream(line.split(""))
								.gather(mapWithIndex((x, value) ->
										Pair.of(new Point(x, y), value)))
				))
				.flatMap(identity())
				.collect(toMap(Pair::left, Pair::right)));
	}
}
