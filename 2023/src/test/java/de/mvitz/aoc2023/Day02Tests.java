package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day02.sumOfPossibleGameIDs;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day02Tests {

	@Test
	void part1_example() {
		var input = contentOf("day02_example.txt");

		var solution = sumOfPossibleGameIDs(input);

		assertThat(solution, is(8L));
	}

	@Test
	void part1() {
		var input = contentOf("day02.txt");

		var solution = sumOfPossibleGameIDs(input);

		assertThat(solution, is(1_867L));
	}
}
