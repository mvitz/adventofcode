package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day19.sumOfAllAcceptedParts;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day19Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day19_example.txt");

		var solution = sumOfAllAcceptedParts(input);

		assertThat(solution, is(19_114L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day19.txt");

		var solution = sumOfAllAcceptedParts(input);

		assertThat(solution, is(446_517L));
	}
}
