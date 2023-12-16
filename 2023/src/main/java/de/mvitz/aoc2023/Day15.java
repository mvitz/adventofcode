
package de.mvitz.aoc2023;

import java.util.Arrays;

final class Day15 {

	private Day15() {
	}

	static long sumOfHashedInitializationSequenceStepsFor(String input) {
		return Arrays.stream(input.split(","))
				.mapToLong(Day15::hash)
				.sum();
	}

	static long hash(String string) {
		return string.chars()
				.reduce(0, (value, character) -> ((value + character) * 17) % 256);
	}
}
