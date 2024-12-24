package de.mvitz.aoc2024.day08;

import de.mvitz.aoc2024.utils.Direction;
import de.mvitz.aoc2024.utils.Grid;
import de.mvitz.aoc2024.utils.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.mvitz.aoc2024.utils.Direction.*;
import static java.util.Arrays.stream;

public final class Day08 {

    private Day08() {
    }

    public static long findUniqueAntinodeLocationsFrom(String input) {
        return AntennaMap.from(input)
                .antinodes((route, _) -> Stream.of(route.applyTo(route.to), route.reverse().applyTo(route.from)))
                .distinct()
                .count();
    }

    public static long findUniqueResonantHarmonicAntinodeLocationsFrom(String input) {
        return AntennaMap.from(input)
                .antinodes(((route, grid) -> {
                    var start = route.to;
                    while (grid.isInBounds(start)) {
                        start = route.applyTo(start);
                    }

                    var antinodes = new ArrayList<Point>();

                    var next = route.reverse().applyTo(start);
                    while (grid.isInBounds(next)) {
                        antinodes.add(next);
                        next = route.reverse().applyTo(next);
                    }

                    return antinodes.stream();
                }))
                .distinct()
                .count();
    }

    private record AntennaMap(Grid<String> grid) {

        private static final String[] FREQUENCIES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("");

        public Stream<Point> antinodes(BiFunction<Route, Grid<String>, Stream<Point>> fn) {
            return stream(FREQUENCIES)
                    .flatMap(frequency -> antinodesFor(frequency, fn));
        }

        private Stream<Point> antinodesFor(String frequency, BiFunction<Route, Grid<String>, Stream<Point>> fn) {
            var antinodes = new ArrayList<Point>();

            var coordinates = new ArrayDeque<>(grid.coordinatesWith(frequency));
            while (!coordinates.isEmpty()) {
                var coordinate = coordinates.pollFirst();
                coordinates.stream()
                        .map(other -> new Route(coordinate, other))
                        .flatMap(route -> fn.apply(route, grid))
                        .filter(grid::isInBounds)
                        .forEach(antinodes::add);
            }

            return antinodes.stream();
        }

        public static AntennaMap from(String input) {
            return new AntennaMap(Grid.from(input));
        }
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
