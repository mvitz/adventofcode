package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day02.sumOfMinimumCubesPower;
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

	@Test
	void part2_example() {
		var input = contentOf("day02_example.txt");

		var solution = sumOfMinimumCubesPower(input);

		assertThat(solution, is(2_286L));
	}

	@Test
	void part2() {
		var input = contentOf("day02.txt");

		var solution = sumOfMinimumCubesPower(input);

		assertThat(solution, is(84_538L));
	}
}
