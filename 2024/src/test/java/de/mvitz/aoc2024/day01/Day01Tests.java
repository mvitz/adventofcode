package de.mvitz.aoc2024.day01;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day01.Day01.findSimilarityScoreForLists;
import static de.mvitz.aoc2024.day01.Day01.findTotalDistanceBetweenLists;
import static org.assertj.core.api.Assertions.assertThat;

class Day01Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				3   4
				4   3
				2   5
				1   3
				3   9
				3   3
				""";

		// when
		var solution = findTotalDistanceBetweenLists(input);

		// then
		assertThat(solution)
				.isEqualTo(11);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day01/input.txt");

		// when
		var solution = findTotalDistanceBetweenLists(input);

		// then
		assertThat(solution)
				.isEqualTo(1_197_984);
	}

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = """
				3   4
				4   3
				2   5
				1   3
				3   9
				3   3
				""";

		// when
		var solution = findSimilarityScoreForLists(input);

		// then
		assertThat(solution)
				.isEqualTo(31);
	}

	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day01/input.txt");

		// when
		var solution = findSimilarityScoreForLists(input);

		// then
		assertThat(solution)
				.isEqualTo(23_387_399);
	}
}
