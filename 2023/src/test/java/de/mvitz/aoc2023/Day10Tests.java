package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2023.Day09.*;
import static de.mvitz.aoc2023.Day10.stepsToReachFarthestPositionFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day10Tests {

	@Test
	@Order(0)
	void part1_example1() {
		var input = contentOf("day10_example1.txt");

		var solution = stepsToReachFarthestPositionFor(input);

		assertThat(solution, is(4L));
	}

	@Test
	@Order(1)
	void part1_example2() {
		var input = contentOf("day10_example2.txt");

		var solution = stepsToReachFarthestPositionFor(input);

		assertThat(solution, is(8L));
	}

	@Test
	@Order(2)
	void part1() {
		var input = contentOf("day10.txt");

		var solution = stepsToReachFarthestPositionFor(input);

		assertThat(solution, is(6_812L));
	}
}
