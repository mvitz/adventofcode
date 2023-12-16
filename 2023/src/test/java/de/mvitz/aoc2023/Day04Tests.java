package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day04.totalNumberOfScratchcards;
import static de.mvitz.aoc2023.Day04.totalScratchcardPoints;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day04Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day04_example.txt");

		var solution = totalScratchcardPoints(input);

		assertThat(solution, is(13L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day04.txt");

		var solution = totalScratchcardPoints(input);

		assertThat(solution, is(20_855L));
	}

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day04_example.txt");

		var solution = totalNumberOfScratchcards(input);

		assertThat(solution, is(30L));
	}

	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day04.txt");

		var solution = totalNumberOfScratchcards(input);

		assertThat(solution, is(5_489_600L));
	}
}
