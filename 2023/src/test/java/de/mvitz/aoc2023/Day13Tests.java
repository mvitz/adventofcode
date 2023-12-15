package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static de.mvitz.aoc2023.Day13.sumOfNotesFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day13Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day13_example.txt");

		var solution = sumOfNotesFor(input);

		assertThat(solution, is(405L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day13.txt");

		var solution = sumOfNotesFor(input);

		assertThat(solution, is(31_956L));
	}
}
