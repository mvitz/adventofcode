package de.mvitz.aoc2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

final class Day03 {

    private Day03() {
    }

    public static int sumOfWrongPackedItemTypePrioritiesFor(String input) {
        return input
                .lines()
                .map(Rucksack::from)
                .map(Rucksack::itemTypesAppearingInBothCompartments)
                .flatMap(Set::stream)
                .mapToInt(ItemType::priority)
                .sum();
    }

    public static int sumOfBadgeItemTypesFor(String input) {
        return input
                .lines()
                .map(Rucksack::from)
                .collect(BatchCollector.batchesOfSize(3)).stream()
                .map(Group::new)
                .map(Group::badge)
                .mapToInt(ItemType::priority)
                .sum();
    }

    private record Group(List<Rucksack> rucksacks) {

        public ItemType badge() {
            return rucksacks.iterator().next()
                    .containedItemTypes().stream()
                    .filter(this::allContainItemWithType)
                    .findFirst()
                    .orElseThrow();
        }

        private boolean allContainItemWithType(ItemType itemType) {
            return rucksacks.stream().allMatch(rucksack -> rucksack.containsItemWithType(itemType));
        }
    }

    private record Rucksack(Compartment first, Compartment second) {

        public boolean containsItemWithType(ItemType itemType) {
            return containedItemTypes().contains(itemType);
        }

        public Set<ItemType> containedItemTypes() {
            final var containedItemTypes = new HashSet<ItemType>();
            containedItemTypes.addAll(first.itemTypes());
            containedItemTypes.addAll(second.itemTypes());
            return containedItemTypes;
        }

        public Set<ItemType> itemTypesAppearingInBothCompartments() {
            return new HashSet<>(first.itemsMatching(second.items()));
        }

        public static Rucksack from(String input) {
            return new Rucksack(
                    Compartment.from(input.substring(0, input.length() / 2)),
                    Compartment.from(input.substring(input.length() / 2)));
        }
    }

    private record Compartment(List<ItemType> items) {

        public Set<ItemType> itemTypes() {
            return new HashSet<>(items);
        }

        public List<ItemType> itemsMatching(List<ItemType> itemTypes) {
            return items.stream()
                    .filter(itemTypes::contains)
                    .toList();
        }

        public static Compartment from(String input) {
            return new Compartment(input
                    .chars()
                    .mapToObj(character -> new ItemType((char) character))
                    .toList());
        }
    }

    private record ItemType(char type) {

        public int priority() {
            if (Character.isUpperCase(type)) {
                return type - 38;
            } else {
                return type - 96;
            }
        }
    }

    private static final class BatchCollector<T> implements Collector<T, List<List<T>>, List<List<T>>> {

        private final int batchSize;

        private BatchCollector(int batchSize) {
            this.batchSize = batchSize;
        }

        @Override
        public Supplier<List<List<T>>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<List<T>>, T> accumulator() {
            return (result, item) -> {
                if (result.isEmpty()) {
                    result.add(new ArrayList<>());
                }

                var currentBatch = result.get(result.size() - 1);
                if (currentBatch.size() >= batchSize) {
                    currentBatch = new ArrayList<>();
                    result.add(currentBatch);
                }

                currentBatch.add(item);
            };
        }

        @Override
        public BinaryOperator<List<List<T>>> combiner() {
            return (first, second) -> {
                first.addAll(second);
                return first;
            };
        }

        @Override
        public Function<List<List<T>>, List<List<T>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(IDENTITY_FINISH);
        }

        public static <T> Collector<T, List<List<T>>, List<List<T>>> batchesOfSize(int batchSize) {
            return new BatchCollector<>(batchSize);
        }
    }
}
