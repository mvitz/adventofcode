package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day05.lowestLocationNumber;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day05Tests {

	@Test
	void part1_example() {
		var input = contentOf("day05_example.txt");

		var solution = lowestLocationNumber(input);

		assertThat(solution, is(35L));
	}

	@Test
	void part1() {
		var input = contentOf("day05.txt");

		var solution = lowestLocationNumber(input);

		assertThat(solution, is(26_273_516L));
	}
}
