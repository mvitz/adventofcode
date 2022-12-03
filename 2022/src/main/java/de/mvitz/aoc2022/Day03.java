package de.mvitz.aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private record Rucksack(Compartment first, Compartment second) {

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
}
