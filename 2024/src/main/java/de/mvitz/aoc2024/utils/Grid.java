package de.mvitz.aoc2024.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
