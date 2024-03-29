package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day08.numberOfGhostStepsToReachTarget;
import static de.mvitz.aoc2023.Day08.numberOfStepsToReachTarget;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day08Tests {

	@Test
	@Order(0)
	void part1_example1() {
		var input = contentOf("day08_example1.txt");

		var solution = numberOfStepsToReachTarget(input);

		assertThat(solution, is(2L));
	}

	@Test
	@Order(1)
	void part1_example2() {
		var input = contentOf("day08_example2.txt");

		var solution = numberOfStepsToReachTarget(input);

		assertThat(solution, is(6L));
	}

	@Test
	@Order(2)
	void part1() {
		var input = contentOf("day08.txt");

		var solution = numberOfStepsToReachTarget(input);

		assertThat(solution, is(11_309L));
	}

	@Test
	@Order(3)
	void part2_example() {
		var input = contentOf("day08_example3.txt");

		var solution = numberOfGhostStepsToReachTarget(input);

		assertThat(solution, is(6L));
	}

	@Test
	@Order(4)
	void part2() {
		var input = contentOf("day08.txt");

		var solution = numberOfGhostStepsToReachTarget(input);

		assertThat(solution, is(13_740_108_158_591L));
	}
}
