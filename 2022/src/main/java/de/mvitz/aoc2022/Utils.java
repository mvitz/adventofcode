package de.mvitz.aoc2022;

import java.nio.file.Files;
import java.nio.file.Path;

final class Utils {

    private Utils() {
    }

    public static String contentOf(String name) {
        try {
            final var resource = Utils.class.getResource("/" + name).toURI();
            return Files.readString(Path.of(resource));
        } catch (Exception e) {
            throw new RuntimeException("Unable to read content of: " + name, e);
        }
    }
}
