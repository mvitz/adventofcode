package de.mvitz.aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static de.mvitz.aoc2022.BatchCollector.batchesOfSize;

final class Day03 {

    private Day03() {
    }

    public static int sumOfWrongPackedItemTypePrioritiesFor(String input) {
        return rucksacksFrom(input)
                .map(Rucksack::itemTypesAppearingInBothCompartments)
                .flatMap(Set::stream)
                .mapToInt(ItemType::priority)
                .sum();
    }

    public static int sumOfBadgeItemTypesFor(String input) {
        return rucksacksFrom(input)
                .collect(batchesOfSize(3)).stream()
                .map(Group::new)
                .map(Group::badge)
                .mapToInt(ItemType::priority)
                .sum();
    }

    private static Stream<Rucksack> rucksacksFrom(String input) {
        return input
                .lines()
                .map(Rucksack::from);
    }

    private record Group(List<Rucksack> rucksacks) {

        public ItemType badge() {
            return first()
                    .containedItemTypes().stream()
                    .filter(this::allContainItemWithType)
                    .findFirst()
                    .orElseThrow();
        }

        private Rucksack first() {
            return rucksacks.iterator().next();
        }

        private boolean allContainItemWithType(ItemType itemType) {
            return rucksacks.stream()
                    .allMatch(rucksack -> rucksack.containsItemWithType(itemType));
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
            return new HashSet<>(first.itemsMatching(second.itemTypes()));
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

        public List<ItemType> itemsMatching(Set<ItemType> itemTypes) {
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
}
