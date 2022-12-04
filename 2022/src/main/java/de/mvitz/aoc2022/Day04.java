package de.mvitz.aoc2022;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.toSet;

final class Day04 {

    private Day04() {
    }

    public static long numberOfFullyContainedAssignmentsFor(String input) {
        return parse(input)
                .map(Day04::fullyContainedAssignmentOf)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .count();
    }

    public static long numberOfOverlappingAssignmentsFor(String input) {
        return parse(input)
                .map(pair -> pair.getKey().overlapps(pair.getValue()))
                .filter(isEqual(true))
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

    private static Stream<Entry<Assignment, Assignment>> parse(String input) {
        return input
                .lines()
                .map(Day04::parseLine);
    }

    private static Entry<Assignment, Assignment> parseLine(String line) {
        final var pair = line.split(",");

        final var first = Assignment.from(pair[0]);
        final var second = Assignment.from(pair[1]);

        return new SimpleEntry<>(first, second);
    }

    private record Assignment(Set<Integer> sections) {

        public boolean overlapps(Assignment other) {
            return sections.stream()
                    .anyMatch(other.sections::contains);
        }

        public boolean isFullyContainedBy(Assignment other) {
            return other.sections.containsAll(sections);
        }

        public static Assignment from(String input) {
            final var startAndEnd = input.split("-");

            final var start = Integer.parseInt(startAndEnd[0]);
            final var end = Integer.parseInt(startAndEnd[1]) + 1;

            return new Assignment(IntStream
                    .range(start, end)
                    .boxed()
                    .collect(toSet()));
        }
    }
}
