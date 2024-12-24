package de.mvitz.aoc2024.day11;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day11.Day11.stonesAfterTwentyFiveIterationsFrom;
import static org.assertj.core.api.Assertions.assertThat;

class Day11Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = "125 17";
		// when
		var solution = stonesAfterTwentyFiveIterationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(55_312);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day11/input.txt").lines().findFirst().orElseThrow();

		// when
		var solution = stonesAfterTwentyFiveIterationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(233_875);
	}
}
