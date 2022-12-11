package de.mvitz.aoc2022;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;

final class Day11 {

    private Day11() {
    }

    public static int findLevelOfMonkeyBusinessAfter20RoundsFor(String input) {
        final var monkeys = parse(input);

        for (int i = 0; i < 20; i++) {
            monkeys.forEach(Monkey::takeTurn);
        }

        return monkeys.stream()
                .sorted(Comparator.comparing(Monkey::numberOfExpectedItems).reversed())
                .mapToInt(Monkey::numberOfExpectedItems)
                .limit(2)
                .reduce((first, second) -> first * second)
                .orElse(-1);
    }

    private static List<Monkey> parse(String input) {
        final var monkeys = new ArrayList<Monkey>();

        Arrays.stream(input.split("\n\n"))
                .map(monkey -> createMonkey(monkey, monkeys))
                .forEach(monkeys::add);

        return monkeys;
    }

    private static Monkey createMonkey(String input, List<Monkey> otherMonkeys) {
        final var lines = input.lines().toList();

        final var operation = parseOperation(lines.get(2));
        final var throwingAction = parseThrowingAction(lines.get(3), lines.get(4), lines.get(5), otherMonkeys);

        final var monkey = new Monkey(operation, throwingAction);

        Arrays.stream(lines.get(1).substring("  Starting items: ".length()).split(", "))
                .mapToInt(Integer::parseInt)
                .forEach(monkey::addItem);

        return monkey;
    }

    private static IntUnaryOperator parseOperation(String line) {
        final String operation = line.substring("  Operation: new = ".length());
        if ("old * old".equals(operation)) {
            return old -> old * old;
        } else if (operation.startsWith("old * ")) {
            final var value = Integer.parseInt(operation.substring("old * ".length()));
            return old -> old * value;
        } else if (operation.startsWith("old + ")) {
            final var value = Integer.parseInt(operation.substring("old + ".length()));
            return old -> old + value;
        } else {
            throw new IllegalArgumentException("Unknown operation: " + line);
        }
    }

    private static IntConsumer parseThrowingAction(String test,
                                                   String ifTrue, String ifFalse,
                                                   List<Monkey> monkeys) {
        if (!test.startsWith("  Test: divisible by ")) {
            throw new IllegalArgumentException("Unknown test: " + test);
        }
        final var testValue = Integer.parseInt(test.substring("  Test: divisible by ".length()));

        if (!ifTrue.startsWith("    If true: throw to monkey ")) {
            throw new IllegalArgumentException("Unknown ifTrue: " + ifTrue);
        }
        final var ifTrueThrowTo = Integer.parseInt(ifTrue.substring("    If true: throw to monkey ".length()));

        if (!ifFalse.startsWith("    If false: throw to monkey ")) {
            throw new IllegalArgumentException("Unknown ifFalse: " + ifFalse);
        }
        final var ifFalseThrowTo = Integer.parseInt(ifFalse.substring("    If false: throw to monkey ".length()));

        return worryLevel -> {
            if (worryLevel % testValue == 0) {
                monkeys.get(ifTrueThrowTo).addItem(worryLevel);
            } else {
                monkeys.get(ifFalseThrowTo).addItem(worryLevel);
            }
        };
    }

    private static final class Monkey {

        private final Deque<Integer> items = new ArrayDeque<>();

        private final IntUnaryOperator operation;
        private final IntConsumer throwingAction;

        private int numberOfExpectedItems = 0;

        private Monkey(IntUnaryOperator operation, IntConsumer throwingAction) {
            this.operation = operation;
            this.throwingAction = throwingAction;
        }

        public void addItem(int worryLevel) {
            items.addLast(worryLevel);
        }

        public void takeTurn() {
            var finished = false;
            while (!finished) {
                finished = inspectNextItem();
            }
        }

        private boolean inspectNextItem() {
            if (items.isEmpty()) {
                return true;
            }

            var worryLevel = items.pollFirst();
            worryLevel = operation.applyAsInt(worryLevel);
            worryLevel /= 3;

            throwingAction.accept(worryLevel);

            numberOfExpectedItems++;

            return false;
        }

        public int numberOfExpectedItems() {
            return numberOfExpectedItems;
        }
    }
}
