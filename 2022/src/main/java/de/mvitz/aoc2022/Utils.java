package de.mvitz.aoc2022;

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
}
