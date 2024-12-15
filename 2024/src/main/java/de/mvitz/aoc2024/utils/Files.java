package de.mvitz.aoc2024.utils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class Files {

	private Files() {
	}

	@SuppressWarnings("java:S112")
	public static String contentOf(String name) {
		try (var in = Files.class.getResourceAsStream("/" + name)) {
			if (in == null) {
				throw new RuntimeException("Unable to find file: " + name);
			}
			return new String(in.readAllBytes(), UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read content of: " + name, e);
		}
	}
}
