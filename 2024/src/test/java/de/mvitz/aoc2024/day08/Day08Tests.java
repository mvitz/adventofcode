package de.mvitz.aoc2024.day08;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day08.Day08.findUniqueAntinodeLocationsFrom;
import static org.assertj.core.api.Assertions.assertThat;

class Day08Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				............
				........0...
				.....0......
				.......0....
				....0.......
				......A.....
				............
				............
				........A...
				.........A..
				............
				............
				""";

		// when
		var solution = findUniqueAntinodeLocationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(14);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day08/input.txt");

		// when
		var solution = findUniqueAntinodeLocationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(273);
	}
}
