package de.mvitz.aoc2024.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public final class Files {

	private Files() {
	}

	@SuppressWarnings("java:S112")
	public static String contentOf(String name) {
		try {
			final var resource = requireNonNull(Files.class.getResource("/" + name)).toURI();
			return java.nio.file.Files.readString(Path.of(resource));
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException("Unable to read content of: " + name, e);
		}
	}
}
