package de.mvitz.aoc2022;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

final class Day04 {

    private Day04() {
    }

    public static long numberOfFullyContainedAssignmentsFor(String input) {
        return input
                .lines()
                .map(Day04::parseLine)
                .map(Day04::fullyContainedAssignmentOf)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .count();
    }

    private static Optional<Assignment> fullyContainedAssignmentOf(
            Entry<Assignment, Assignment> pair) {
        final var first = pair.getKey();
        final var second = pair.getValue();

        if (first.isFullyContainedBy(second)) {
            return Optional.of(first);
        } else if (second.isFullyContainedBy(first)) {
            return Optional.of(second);
        } else {
            return Optional.empty();
        }
    }

    private static Entry<Assignment, Assignment> parseLine(String line) {
        final var pair = line.split(",");

        final var first = Assignment.from(pair[0]);
        final var second = Assignment.from(pair[1]);

        return new SimpleEntry<>(first, second);
    }

    private record Assignment(List<Integer> sections) {

        public boolean isFullyContainedBy(Assignment other) {
            return sections.stream()
                    .allMatch(other.sections::contains);
        }

        public static Assignment from(String input) {
            final var startAndEnd = input.split("-");

            final var start = Integer.parseInt(startAndEnd[0]);
            final var end = Integer.parseInt(startAndEnd[1]) + 1;

            return new Assignment(IntStream
                    .range(start, end)
                    .boxed()
                    .toList());
        }
    }
}
