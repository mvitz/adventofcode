package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static de.mvitz.aoc2023.Day06.productOfRecordBeatingWaysFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
}
