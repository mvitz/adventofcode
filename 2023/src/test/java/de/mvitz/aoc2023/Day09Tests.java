package de.mvitz.aoc2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2023.Day09.*;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day09Tests {

	@ParameterizedTest
	@Order(0)
	@CsvSource(textBlock = """
			0 3 6 9 12 15,18
			1 3 6 10 15 21,28
			10 13 16 21 30 45,68
						""")
	void part1_forwardExtrapolationFor(String historyLine, long expectedExtrapolation) {
		var history = parseHistory(historyLine);

		var extrapolation = forwardExtrapolationFor(history);

		assertThat(extrapolation, is(expectedExtrapolation));
	}

	@ParameterizedTest
	@Order(1)
	@CsvSource(textBlock = """
			0 3 6 9 12 15,3 3 3 3 3
			3 3 3 3 3,0 0 0 0
			1 3 6 10 15 21,2 3 4 5 6
			2 3 4 5 6,1 1 1 1
			1 1 1 1,0 0 0
			10 13 16 21 30 45,3 3 5 9 15
			3 3 5 9 15,0 2 4 6
			0 2 4 6,2 2 2
			2 2 2,0 0
						""")
	void part1_differencesBetweenValues(String valuesLine, String expectedDifferences) {
		var values = parseHistory(valuesLine);

		var differences = differencesBetweenValues(values);

		assertThat(differences, is(parseHistory(expectedDifferences)));
	}

	@Test
	@Order(2)
	void part1_example() {
		var input = contentOf("day09_example.txt");

		var solution = sumOfForwardExtrapolatedValues(input);

		assertThat(solution, is(114L));
	}

	@Test
	@Order(3)
	void part1() {
		var input = contentOf("day09.txt");

		var solution = sumOfForwardExtrapolatedValues(input);

		assertThat(solution, is(1_798_691_765L));
	}

	@ParameterizedTest
	@Order(4)
	@CsvSource(textBlock = """
			0 3 6 9 12 15,-3
			1 3 6 10 15 21,0
			10 13 16 21 30 45,5
						""")
	void part2_backwardsExtrapolationFor(String historyLine, long expectedExtrapolation) {
		var history = parseHistory(historyLine);

		var extrapolation = backwardsExtrapolationFor(history);

		assertThat(extrapolation, is(expectedExtrapolation));
	}

	@Test
	@Order(5)
	void part2_example() {
		var input = contentOf("day09_example.txt");

		var solution = sumOfBackwardsExtrapolatedValues(input);

		assertThat(solution, is(2L));
	}

	@Test
	@Order(6)
	void part2() {
		var input = contentOf("day09.txt");

		var solution = sumOfBackwardsExtrapolatedValues(input);

		assertThat(solution, is(1_104L));
	}
}
