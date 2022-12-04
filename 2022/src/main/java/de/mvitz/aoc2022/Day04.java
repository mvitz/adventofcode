package de.mvitz.aoc2022;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

final class Day04 {

    private Day04() {
    }

    public static long numberOfFullyContainedAssignmentsFor(String input) {
        return numberOf(AssignmentPair::containsFullyContainedAssignment, input);
    }

    public static long numberOfOverlappingAssignmentsFor(String input) {
        return numberOf(AssignmentPair::containsOverlappingAssignments, input);
    }

    private static long numberOf(
            Predicate<AssignmentPair> condition, String input) {
        return input
                .lines()
                .map(AssignmentPair::from)
                .filter(condition)
                .count();
    }

    private record AssignmentPair(Assignment first, Assignment second) {

        public boolean containsFullyContainedAssignment() {
            return first.isFullyContainedBy(second)
                    || second.isFullyContainedBy(first);
        }

        public boolean containsOverlappingAssignments() {
            return first.overlaps(second);
        }

        public static AssignmentPair from(String input) {
            final var pair = input.split(",");
            return new AssignmentPair(
                    Assignment.from(pair[0]),
                    Assignment.from(pair[1]));
        }
    }

    private record Assignment(Set<Integer> sections) {

        public boolean overlaps(Assignment other) {
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
