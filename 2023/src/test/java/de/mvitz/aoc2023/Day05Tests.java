package de.mvitz.aoc2023;

import org.junit.jupiter.api.*;

import static de.mvitz.aoc2023.Day05.lowestLocationNumberValues;
import static de.mvitz.aoc2023.Day05.lowestLocationNumberRanges;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day05Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day05_example.txt");

		var solution = lowestLocationNumberValues(input);

		assertThat(solution, is(35L));
	}

	@Disabled("Takes to long to run regularly")
	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day05.txt");

		var solution = lowestLocationNumberValues(input);

		assertThat(solution, is(26_273_516L));
	}

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day05_example.txt");

		var solution = lowestLocationNumberRanges(input);

		assertThat(solution, is(46L));
	}

	@Disabled("Takes to long to run regularly")
	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day05.txt");

		var solution = lowestLocationNumberRanges(input);

		assertThat(solution, is(34_039_469L));
	}
}
