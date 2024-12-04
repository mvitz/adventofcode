package de.mvitz.aoc2024.day04;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day04.Day04.findNumberOfXDashMasAppearancesIn;
import static de.mvitz.aoc2024.day04.Day04.findNumberOfXmasAppearancesIn;
import static org.assertj.core.api.Assertions.assertThat;

class Day04Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				MMMSXXMASM
				MSAMXMSMSA
				AMXSXMAAMM
				MSAMASMSMX
				XMASAMXAMM
				XXAMMXXAMA
				SMSMSASXSS
				SAXAMASAAA
				MAMMMXMMMM
				MXMXAXMASX
				""";

		// when
		var solution = findNumberOfXmasAppearancesIn(input);

		// then
		assertThat(solution)
				.isEqualTo(18);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day04/input.txt");

		// when
		var solution = findNumberOfXmasAppearancesIn(input);

		// then
		assertThat(solution)
				.isEqualTo(2_462);
	}

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = """
				MMMSXXMASM
				MSAMXMSMSA
				AMXSXMAAMM
				MSAMASMSMX
				XMASAMXAMM
				XXAMMXXAMA
				SMSMSASXSS
				SAXAMASAAA
				MAMMMXMMMM
				MXMXAXMASX
				""";

		// when
		var solution = findNumberOfXDashMasAppearancesIn(input);

		// then
		assertThat(solution)
				.isEqualTo(9);
	}

	@Test
	@Order(1)
	void part2() {
		// given
		var input = Files.contentOf("day04/input.txt");

		// when
		var solution = findNumberOfXDashMasAppearancesIn(input);

		// then
		assertThat(solution)
				.isEqualTo(1_877);
	}
}
