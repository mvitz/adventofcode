package de.mvitz.aoc2024.day11;

import java.util.stream.Stream;

import static java.util.Arrays.stream;

public final class Day11 {

	private Day11() {
	}

	public static long stonesAfterTwentyFiveIterationsFrom(String input) {
		var stones = stream(input.split(" "));
		for (int i = 0; i < 25; i++) {
			stones = stones.flatMap(Day11::nextIterationFor);
		}
		return stones.count();
	}

	private static Stream<String> nextIterationFor(String stone) {
		return switch (stone) {
			case "0" -> Stream.of("1");
			case String s when (s.length() % 2 == 0) -> split(stone);
			default -> Stream.of(Long.toString(Long.parseLong(stone) * 2024));
		};
	}

	private static Stream<String> split(String stone) {
		var middle = stone.length() / 2;
		return Stream.of(
				Long.toString(Long.parseLong(stone.substring(0, middle))),
				Long.toString(Long.parseLong(stone.substring(middle))));
	}
}
