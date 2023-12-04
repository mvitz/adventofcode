package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day04.totalScratchcardPoints;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day04Tests {

	@Test
	void part1_example() {
		var input = contentOf("day04_example.txt");

		var solution = totalScratchcardPoints(input);

		assertThat(solution, is(13L));
	}

	@Test
	void part1() {
		var input = contentOf("day04.txt");

		var solution = totalScratchcardPoints(input);

		assertThat(solution, is(20_855L));
	}
}
