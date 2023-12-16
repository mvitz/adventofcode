package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day06.numberOfRecordingBeatingWaysFor;
import static de.mvitz.aoc2023.Day06.productOfRecordBeatingWaysFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day06Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day06_example.txt");

		var solution = productOfRecordBeatingWaysFor(input);

		assertThat(solution, is(288L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day06.txt");

		var solution = productOfRecordBeatingWaysFor(input);

		assertThat(solution, is(440_000L));
	}

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day06_example.txt");

		var solution = numberOfRecordingBeatingWaysFor(input);

		assertThat(solution, is(71_503L));
	}

	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day06.txt");

		var solution = numberOfRecordingBeatingWaysFor(input);

		assertThat(solution, is(26_187_338L));
	}
}
