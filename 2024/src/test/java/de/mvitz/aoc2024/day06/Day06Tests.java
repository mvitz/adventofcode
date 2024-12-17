package de.mvitz.aoc2024.day06;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day06.Day06.numberOfDistinctGuardPositions;
import static de.mvitz.aoc2024.day06.Day06.numberOfObstructionPositionsToProduceLoopFor;
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

	@Test
	@Order(2)
	void part2_example() {
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
		var solution = numberOfObstructionPositionsToProduceLoopFor(input);

		// then
		assertThat(solution)
				.isEqualTo(6);
	}

	@Disabled("Takes to long to run regularly")
	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day06/input.txt");

		// when
		var solution = numberOfObstructionPositionsToProduceLoopFor(input);

		// then
		assertThat(solution)
				.isEqualTo(1_770);
	}
}
