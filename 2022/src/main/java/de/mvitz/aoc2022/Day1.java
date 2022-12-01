package de.mvitz.aoc2022;

import java.util.Arrays;

import static java.util.Comparator.comparing;

public class Day1 {

    public static Elf findElfWithMostCaloriesFrom(String input) {
        return Arrays.stream(input.split("\n\n"))
            .map(elfInput -> new Elf(elfInput.lines()
                .mapToLong(Long::parseLong)
                .sum()))
            .sorted(comparing(Elf::calories).reversed())
            .findFirst()
            .orElseThrow();
    }

    public record Elf(long calories) {
    }
}
