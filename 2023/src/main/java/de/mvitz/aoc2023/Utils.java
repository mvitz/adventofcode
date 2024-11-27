package de.mvitz.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

final class Utils {

	private Utils() {
	}

	@SuppressWarnings("java:S112")
	public static String contentOf(String name) {
		try {
			final var resource = requireNonNull(Utils.class.getResource("/" + name)).toURI();
			return Files.readString(Path.of(resource));
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException("Unable to read content of: " + name, e);
		}
	}

	public static String firstLineOf(String name) {
		return contentOf(name)
				.lines()
				.findFirst()
				.orElseThrow();
	}

	public static boolean isBetween(int value, int min, int max) {
		return value > min && value < max;
	}

	public record Pair<T, U>(T first, U second) {

		public static <T, U> Pair<T, U> of(T first, U second) {
			return new Pair<>(first, second);
		}
	}

	public record Point(int x, int y) {

		public Point above() {
			return new Point(x, y - 1);
		}

		public Point below() {
			return new Point(x, y + 1);
		}

		public Point left() {
			return new Point(x - 1, y);
		}

		public Point right() {
			return new Point(x + 1, y);
		}

		public boolean inSameColumnAs(Point other) {
			return isInColumn(other.x);
		}

		public boolean isInColumn(int column) {
			return column == x;
		}

		public boolean inSameRowAs(Point other) {
			return isInRow(other.y);
		}

		public boolean isInRow(int row) {
			return row == y;
		}

		public boolean isInBoundsOf(int xmax, int ymax) {
			return isInBoundsOf(0, xmax, 0, ymax);
		}

		public boolean isInBoundsOf(int xmin, int xmax, int ymin, int ymax) {
			return isBetween(x, xmin - 1, xmax + 1)
				   && isBetween(y, ymin - 1, ymax + 1);
		}
	}
}
