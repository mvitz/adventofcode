package de.mvitz.aoc2024.day10;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day10.Day10.sumOfTrailheadRatingsFor;
import static de.mvitz.aoc2024.day10.Day10.sumOfTrailheadScoresFor;
import static org.assertj.core.api.Assertions.assertThat;

class Day10Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				89010123
				78121874
				87430965
				96549874
				45678903
				32019012
				01329801
				10456732
				""";

		// when
		var solution = sumOfTrailheadScoresFor(input);

		// then
		assertThat(solution)
				.isEqualTo(36);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day10/input.txt");

		// when
		var solution = sumOfTrailheadScoresFor(input);

		// then
		assertThat(solution)
				.isEqualTo(659);
	}

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = """
				89010123
				78121874
				87430965
				96549874
				45678903
				32019012
				01329801
				10456732
				""";

		// when
		var solution = sumOfTrailheadRatingsFor(input);

		// then
		assertThat(solution)
				.isEqualTo(81);
	}

	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day10/input.txt");

		// when
		var solution = sumOfTrailheadRatingsFor(input);

		// then
		assertThat(solution)
				.isEqualTo(1_463);
	}
}
