package de.mvitz.aoc2024.utils;

import java.util.*;
import java.util.function.Function;

import static de.mvitz.aoc2024.utils.Gatherers.mapWithIndex;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class Grid<T> {

	private final Map<Point, T> fields;

	private Grid(Map<Point, T> fields) {
		this.fields = fields;
	}

	public Grid<T> with(Point coordinate, T value) {
		var newFields = new HashMap<>(fields);
		newFields.put(coordinate, value);
		return new Grid<>(newFields);
	}

	public Optional<T> valueAt(Point coordinate) {
		return Optional.ofNullable(fields.get(coordinate));
	}

	public List<Point> coordinatesWith(T value) {
		return fields.entrySet().stream()
				.filter(field -> field.getValue().equals(value))
				.map(Map.Entry::getKey)
				.toList();
	}

	@SuppressWarnings("preview")
	public static <T> Grid<T> from(String input, Function<String, T> fn) {
		return new Grid<>(input.lines()
				.gather(mapWithIndex((y, line) ->
						Arrays.stream(line.split(""))
								.gather(mapWithIndex((x, value) ->
										Pair.of(new Point(x, y), fn.apply(value))))
				))
				.flatMap(identity())
				.collect(toMap(Pair::left, Pair::right)));
	}

	public static Grid<String> from(String input) {
		return from(input, identity());
	}
}
