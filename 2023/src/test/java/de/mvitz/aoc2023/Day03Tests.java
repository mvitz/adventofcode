package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day03.sumOfGearRatios;
import static de.mvitz.aoc2023.Day03.sumOfPartNumbers;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day03Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day03_example.txt");

		var solution = sumOfPartNumbers(input);

		assertThat(solution, is(4_361L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day03.txt");

		var solution = sumOfPartNumbers(input);

		assertThat(solution, is(520_135L));
	}

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day03_example.txt");

		var solution = sumOfGearRatios(input);

		assertThat(solution, is(467_835L));
	}

	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day03.txt");

		var solution = sumOfGearRatios(input);

		assertThat(solution, is(72_514_855L));
	}
}
