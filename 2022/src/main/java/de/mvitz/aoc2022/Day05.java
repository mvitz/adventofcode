package de.mvitz.aoc2022;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

final class Day05 {

    private Day05() {
    }

    public static String findCrateOnTopMessageFor(String input) {
        final var startingStackAndRearrangementProcedure = input.split("\n\n");

        final var stacks = Stacks.from(startingStackAndRearrangementProcedure[0]);

        startingStackAndRearrangementProcedure[1]
                .lines()
                .forEach(line -> {
                    final var m = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)").matcher(line);
                    if (m.matches()) {
                        final var number = Integer.parseInt(m.group(1));
                        final var from = Integer.parseInt(m.group(2));
                        final var to = Integer.parseInt(m.group(3));
                        stacks.move(number, from, to);
                    }
                });

        return stacks.topCrates().stream().collect(joining(""));
    }

    private static final class Stacks {

        private final Stack<String>[] stacks;

        private Stacks(int numberOfStacks) {
            this.stacks = new Stack[numberOfStacks];
            IntStream.range(0, numberOfStacks).forEach(number -> stacks[number] = new Stack<>());
        }

        public List<String> topCrates() {
            return Arrays.stream(stacks)
                    .map(Stack::peek)
                    .toList();
        }

        public void push(int stack, String crate) {
            stacks[stack - 1].push(crate);
        }

        private String pop(int stack) {
            return stacks[stack - 1].pop();
        }

        public void move(int number, int from, int to) {
            for (int i = 0; i < number; i++) {
                final var crate = pop(from);
                push(to, crate);
            }
        }

        public static Stacks from(String input) {
            final var startingStack = input
                    .lines()
                    .sorted((first, second) -> -1)
                    .toList();

            final var firstLine = startingStack.iterator().next();
            final var numberOfStacks = firstLine.substring(firstLine.length() - 1);

            final var stacks = new Stacks(Integer.parseInt(numberOfStacks));

            startingStack.subList(1, startingStack.size())
                    .forEach(line -> {
                        var i = 1;
                        do {
                            final var crate = line.substring(i, i + 1);
                            if (!crate.isBlank()) {
                                stacks.push((i / 4) + 1, crate);
                            }
                            i += 4;
                        } while (i < line.length());
                    });

            return stacks;
        }
    }
}
