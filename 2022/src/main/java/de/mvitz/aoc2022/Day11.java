package de.mvitz.aoc2022;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static java.util.Comparator.comparing;

final class Day11 {

    private Day11() {
    }

    public static long findLevelOfMonkeyBusinessAfterRoundsFor(
            String input,
            UnaryOperator<BigInteger> worryLevelReduction,
            int rounds) {
        final var monkeys = parse(input, worryLevelReduction);

        for (var i = 0; i < rounds; i++) {
            monkeys.forEach(Monkey::takeTurn);
        }

        return monkeys.stream()
                .sorted(comparing(Monkey::numberOfExpectedItems).reversed())
                .mapToLong(Monkey::numberOfExpectedItems)
                .limit(2)
                .reduce((first, second) -> first * second)
                .orElse(-1);
    }

    private static List<Monkey> parse(String input,
                                      UnaryOperator<BigInteger> worryLevelReduction) {
        final var monkeys = new ArrayList<Monkey>();

        Arrays.stream(input.split("\n\n"))
                .map(monkey -> createMonkey(monkey, worryLevelReduction, monkeys))
                .forEach(monkeys::add);

        return monkeys;
    }

    private static Monkey createMonkey(String input,
                                       UnaryOperator<BigInteger> worryLevelReduction,
                                       List<Monkey> otherMonkeys) {
        final var lines = input.lines().toList();

        final var operation = parseOperation(lines.get(2));
        final var throwingAction = parseThrowingAction(lines.get(3), lines.get(4), lines.get(5), otherMonkeys);

        final var monkey = new Monkey(operation, worryLevelReduction, throwingAction);

        Arrays.stream(lines.get(1).substring("  Starting items: ".length()).split(", "))
                .map(BigInteger::new)
                .forEach(monkey::addItem);

        return monkey;
    }

    private static UnaryOperator<BigInteger> parseOperation(String line) {
        final String operation = line.substring("  Operation: new = ".length());
        if ("old * old".equals(operation)) {
            return old -> old.multiply(old);
        } else if (operation.startsWith("old * ")) {
            final var value = new BigInteger(operation.substring("old * ".length()));
            return old -> old.multiply(value);
        } else if (operation.startsWith("old + ")) {
            final var value = new BigInteger(operation.substring("old + ".length()));
            return old -> old.add(value);
        } else {
            throw new IllegalArgumentException("Unknown operation: " + line);
        }
    }

    private static Consumer<BigInteger> parseThrowingAction(String test,
                                                            String ifTrue, String ifFalse,
                                                            List<Monkey> monkeys) {
        if (!test.startsWith("  Test: divisible by ")) {
            throw new IllegalArgumentException("Unknown test: " + test);
        }
        final var testValue = new BigInteger(test.substring("  Test: divisible by ".length()));

        if (!ifTrue.startsWith("    If true: throw to monkey ")) {
            throw new IllegalArgumentException("Unknown ifTrue: " + ifTrue);
        }
        final var ifTrueThrowTo = Integer.parseInt(ifTrue.substring("    If true: throw to monkey ".length()));

        if (!ifFalse.startsWith("    If false: throw to monkey ")) {
            throw new IllegalArgumentException("Unknown ifFalse: " + ifFalse);
        }
        final var ifFalseThrowTo = Integer.parseInt(ifFalse.substring("    If false: throw to monkey ".length()));

        return worryLevel -> {
            if (BigInteger.ZERO.equals(worryLevel.remainder(testValue))) {
                monkeys.get(ifTrueThrowTo).addItem(worryLevel);
            } else {
                monkeys.get(ifFalseThrowTo).addItem(worryLevel);
            }
        };
    }

    private static final class Monkey {

        private final Deque<BigInteger> items = new ArrayDeque<>();

        private final UnaryOperator<BigInteger> operation;
        private final UnaryOperator<BigInteger> worryLevelReduction;
        private final Consumer<BigInteger> throwingAction;

        private long numberOfExpectedItems = 0;

        private Monkey(UnaryOperator<BigInteger> operation,
                       UnaryOperator<BigInteger> worryLevelReduction,
                       Consumer<BigInteger> throwingAction) {
            this.operation = operation;
            this.worryLevelReduction = worryLevelReduction;
            this.throwingAction = throwingAction;
        }

        public void addItem(BigInteger worryLevel) {
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
            worryLevel = operation.apply(worryLevel);
            worryLevel = worryLevelReduction.apply(worryLevel);

            throwingAction.accept(worryLevel);

            numberOfExpectedItems++;

            return false;
        }

        public long numberOfExpectedItems() {
            return numberOfExpectedItems;
        }
    }
}
