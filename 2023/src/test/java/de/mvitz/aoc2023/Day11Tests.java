package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day11.sumOfAllShortestPathsBetweenGalaxyPairs;
import static de.mvitz.aoc2023.Day11.sumOfAllShortestPathsBetweenOlderGalaxyPairs;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

	@Test
	@Order(2)
	void part2_example1() {
		var input = contentOf("day11_example.txt");

		var solution = sumOfAllShortestPathsBetweenOlderGalaxyPairs(input, 10);

		assertThat(solution, is(1_030L));
	}

	@Test
	@Order(3)
	void part2_example2() {
		var input = contentOf("day11_example.txt");

		var solution = sumOfAllShortestPathsBetweenOlderGalaxyPairs(input, 100);

		assertThat(solution, is(8_410L));
	}

	@Test
	@Order(4)
	void part2() {
		var input = contentOf("day11.txt");

		var solution = sumOfAllShortestPathsBetweenOlderGalaxyPairs(input, 1_000_000);

		assertThat(solution, is(613_686_987_427L));
	}
}
