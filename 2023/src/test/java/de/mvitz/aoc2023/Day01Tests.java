package de.mvitz.aoc2023;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2023.Day01.sumOfCalibrationValues;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day01Tests {

	@Test
	void part1_example() {
		var input = contentOf("day01_example.txt");

		var solution = sumOfCalibrationValues(input);

		assertThat(solution, is(142L));
	}

	@Test
	void part1() {
		var input = contentOf("day01.txt");

		var solution = sumOfCalibrationValues(input);

		assertThat(solution, is(55_834L));
	}
}
