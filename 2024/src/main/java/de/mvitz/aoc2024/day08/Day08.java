package de.mvitz.aoc2024.day08;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.mvitz.aoc2024.utils.Direction.*;
import static java.util.Arrays.stream;

public final class Day08 {

    private Day08() {
    }

    public static long findUniqueAntinodeLocationsFrom(String input) {
        var map = Grid.from(input);
        return stream("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split(""))
                .flatMap(antenna -> antinodesFor(antenna, map))
                .distinct()
                .count();
    }

    private static Stream<Point> antinodesFor(String antenna, Grid<String> map) {
        var antinodes = new ArrayList<Point>();

        var coordinates = new ArrayDeque<>(map.coordinatesWith(antenna));
        while (!coordinates.isEmpty()) {
            var coordinate = coordinates.pollFirst();
            coordinates.stream()
                    .map(other -> new Route(coordinate, other))
                    .flatMap(route -> Stream.of(route.applyTo(route.to), route.reverse().applyTo(route.from)))
                    .filter(map::isInBounds)
                    .forEach(antinodes::add);
        }

        return antinodes.stream();
    }

    private record Route(Point from, Point to) {

        public Route reverse() {
            return new Route(to, from);
        }

        public Point applyTo(Point other) {
            var result = other;
            for (var step : steps()) {
                result = step.from(result);
            }
            return result;
        }

        private List<Direction> steps() {
            var steps = new ArrayList<Direction>();

            if (from.x() > to.x()) {
                IntStream.range(to.x(), from.x())
                        .mapToObj(_ -> LEFT)
                        .forEach(steps::add);
            } else {
                IntStream.range(from.x(), to.x())
                        .mapToObj(_ -> RIGHT)
                        .forEach(steps::add);
            }

            if (from.y() > to.y()) {
                IntStream.range(to.y(), from.y())
                        .mapToObj(_ -> UP)
                        .forEach(steps::add);
            } else {
                IntStream.range(from.y(), to.y())
                        .mapToObj(_ -> DOWN)
                        .forEach(steps::add);
            }

            return steps;
        }
    }
}
