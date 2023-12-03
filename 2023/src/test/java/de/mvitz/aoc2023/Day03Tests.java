package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day03.sumOfPartNumbers;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day03Tests {

	@Test
	void part1_example() {
		var input = contentOf("day03_example.txt");

		var solution = sumOfPartNumbers(input);

		assertThat(solution, is(4_361L));
	}

	@Test
	void part1() {
		var input = contentOf("day03.txt");

		var solution = sumOfPartNumbers(input);

		assertThat(solution, is(520_135L));
	}
}
