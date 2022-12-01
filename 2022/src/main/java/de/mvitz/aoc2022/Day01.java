package de.mvitz.aoc2022;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

final class Day01 {

    private Day01() {
    }

    public static long findMostCaloriesOfSingleElf(String input) {
        return elvesOf(input)
            .max(comparing(Elf::calories))
            .map(Elf::calories)
            .orElseThrow();
    }

    public static long findCalorieSumOfTopThreeElves(String input) {
        return elvesOf(input)
            .sorted(comparing(Elf::calories).reversed())
            .limit(3)
            .mapToLong(Elf::calories)
            .sum();
    }

    private static Stream<Elf> elvesOf(String input) {
        return Arrays.stream(input.split("\n\n"))
            .map(elfInput -> new Elf(elfInput.lines()
                .mapToLong(Long::parseLong)
                .sum()));
    }

    public record Elf(long calories) {
    }
}
