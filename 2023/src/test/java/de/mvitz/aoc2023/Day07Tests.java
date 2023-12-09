package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static de.mvitz.aoc2023.Day07.totalWinningsOf;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day07Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day07_example.txt");

		var solution = totalWinningsOf(input);

		assertThat(solution, is(6_440L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day07.txt");

		var solution = totalWinningsOf(input);

		assertThat(solution, is(251_029_473L));
	}
}
