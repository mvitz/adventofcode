package de.mvitz.aoc2024.day07;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.mvitz.aoc2024.day07.Day07.Equation.Operator.*;
import static de.mvitz.aoc2024.day07.Day07.totalCalibrationResultFrom;
import static org.assertj.core.api.Assertions.assertThat;

class Day07Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				190: 10 19
				3267: 81 40 27
				83: 17 5
				156: 15 6
				7290: 6 8 6 15
				161011: 16 10 13
				192: 17 8 14
				21037: 9 7 18 13
				292: 11 6 16 20
				""";

		// when
		var solution = totalCalibrationResultFrom(input, List.of(ADD, MULTIPLY));

		// then
		assertThat(solution)
				.isEqualTo(3_749);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day07/input.txt");

		// when
		var solution = totalCalibrationResultFrom(input, List.of(ADD, MULTIPLY));

		// then
		assertThat(solution)
				.isEqualTo(663_613_490_587L);
	}

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = """
				190: 10 19
				3267: 81 40 27
				83: 17 5
				156: 15 6
				7290: 6 8 6 15
				161011: 16 10 13
				192: 17 8 14
				21037: 9 7 18 13
				292: 11 6 16 20
				""";

		// when
		var solution = totalCalibrationResultFrom(input, List.of(ADD, MULTIPLY, CONCATENATION));

		// then
		assertThat(solution)
				.isEqualTo(11_387);
	}

	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day07/input.txt");

		// when
		var solution = totalCalibrationResultFrom(input, List.of(ADD, MULTIPLY, CONCATENATION));

		// then
		assertThat(solution)
				.isEqualTo(110_365_987_435_001L);
	}
}
