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
			final var resource = requireNonNull(Utils.class.getResource(STR."/\{name}")).toURI();
			return Files.readString(Path.of(resource));
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(STR."Unable to read content of: \{name}", e);
		}
	}

	public static String firstLineOf(String name) {
		return contentOf(name)
				.lines()
				.findFirst()
				.orElseThrow();
	}

	public record Pair<T, U>(T first, U second) {

		public static <T, U> Pair<T, U> of(T first, U second) {
			return new Pair<>(first, second);
		}
	}
}
