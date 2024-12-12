package de.mvitz.aoc2024.day06.day05;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day06.Day06.numberOfDistinctGuardPositions;
import static org.assertj.core.api.Assertions.assertThat;

class Day06Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				....#.....
				.........#
				..........
				..#.......
				.......#..
				..........
				.#..^.....
				........#.
				#.........
				......#...
				""";

		// when
		var solution = numberOfDistinctGuardPositions(input);

		// then
		assertThat(solution)
				.isEqualTo(41);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day06/input.txt");

		// when
		var solution = numberOfDistinctGuardPositions(input);

		// then
		assertThat(solution)
				.isEqualTo(5_086);
	}
}
