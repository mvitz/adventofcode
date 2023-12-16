package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day01.sumOfCalibrationValues;
import static de.mvitz.aoc2023.Day01.sumOfRealCalibrationValues;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day01Tests {

	@Test
	@Order(0)
	void part1_example() {
		var input = contentOf("day01_example1.txt");

		var solution = sumOfCalibrationValues(input);

		assertThat(solution, is(142L));
	}

	@Test
	@Order(1)
	void part1() {
		var input = contentOf("day01.txt");

		var solution = sumOfCalibrationValues(input);

		assertThat(solution, is(55_834L));
	}

	@Test
	@Order(2)
	void part2_example() {
		var input = contentOf("day01_example2.txt");

		var solution = sumOfRealCalibrationValues(input);

		assertThat(solution, is(281L));
	}

	@Test
	@Order(3)
	void part2() {
		var input = contentOf("day01.txt");

		var solution = sumOfRealCalibrationValues(input);

		assertThat(solution, is(53_221L));
	}
}
