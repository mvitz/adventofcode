package de.mvitz.aoc2022;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

final class Day05 {

    private Day05() {
    }

    private static final Pattern REARRANGEMENT_PROCEDURE_PATTERN =
            Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    public static String findCrateOnTopMessageFor(int crateMoverVersion, String input) {
        final var startingStackAndRearrangementProcedure = input.split("\n\n");

        final var stacks = Stacks.from(crateMoverVersion, startingStackAndRearrangementProcedure[0]);

        startingStackAndRearrangementProcedure[1]
                .lines()
                .forEach(rearrangementProcedure -> execute(stacks, rearrangementProcedure));

        return String.join("", stacks.topCrates());
    }

    private static void execute(Stacks stacks, String line) {
        final var matcher = REARRANGEMENT_PROCEDURE_PATTERN.matcher(line);
        if (matcher.matches()) {
            final var number = Integer.parseInt(matcher.group(1));
            final var from = Integer.parseInt(matcher.group(2));
            final var to = Integer.parseInt(matcher.group(3));
            stacks.move(number, from, to);
        }
    }

    private static final class Stacks {

        private final int crateMoverVersion;
        private final Deque<String>[] stacks;

        @SuppressWarnings("unchecked")
        private Stacks(int crateMoverVersion, int numberOfStacks) {
            this.crateMoverVersion = crateMoverVersion;
            this.stacks = new Deque[numberOfStacks];
            IntStream.range(0, numberOfStacks)
                    .forEach(number -> stacks[number] = new ArrayDeque<>());
        }

        public List<String> topCrates() {
            return Arrays.stream(stacks)
                    .map(Deque::peekLast)
                    .toList();
        }

        public void push(int stack, String crate) {
            stacks[stack - 1].addLast(crate);
        }

        private String pop(int stack) {
            return stacks[stack - 1].pollLast();
        }

        public void move(int number, int from, int to) {
            if (crateMoverVersion == 9000) {
                for (int i = 0; i < number; i++) {
                    final var crate = pop(from);
                    push(to, crate);
                }
            } else if (crateMoverVersion == 9001) {
                final var tmp = new ArrayDeque<String>();
                for (int i = 0; i < number; i++) {
                    tmp.addFirst(pop(from));
                }
                tmp.forEach(crate -> push(to, crate));
            }
        }

        public static Stacks from(int crateMoverVersion, String input) {
            final var startingStack = input
                    .lines()
                    .sorted((first, second) -> -1)
                    .toList();

            final var firstLine = startingStack.iterator().next();
            final var numberOfStacks = firstLine.substring(firstLine.length() - 1);

            final var stacks = new Stacks(crateMoverVersion, Integer.parseInt(numberOfStacks));

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
