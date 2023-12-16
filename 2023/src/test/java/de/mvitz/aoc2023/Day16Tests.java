package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static de.mvitz.aoc2023.Day16.numberOfEnergizedTilesFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day16Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day16_example.txt");

		var solution = numberOfEnergizedTilesFor(input);

		assertThat(solution, is(46));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day16.txt");

		var solution = numberOfEnergizedTilesFor(input);

		assertThat(solution, is(7_927));
	}
}
