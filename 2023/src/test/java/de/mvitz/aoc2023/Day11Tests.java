package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static de.mvitz.aoc2023.Day11.sumOfAllShortestPathsBetweenGalaxyPairs;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day11Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day11_example.txt");

		var solution = sumOfAllShortestPathsBetweenGalaxyPairs(input);

		assertThat(solution, is(374L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day11.txt");

		var solution = sumOfAllShortestPathsBetweenGalaxyPairs(input);

		assertThat(solution, is(9_214_785L));
	}
}
