package de.mvitz.aoc2023;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day16.maximumNumberOfEnergizedTilesFor;
import static de.mvitz.aoc2023.Day16.numberOfEnergizedTilesFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day16_example.txt");

		var solution = maximumNumberOfEnergizedTilesFor(input);

		assertThat(solution, is(51));
	}

	@Disabled("Takes to long to run regularly")
	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day16.txt");

		var solution = maximumNumberOfEnergizedTilesFor(input);

		assertThat(solution, is(8_246));
	}
}
