package de.mvitz.aoc2023;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static de.mvitz.aoc2023.Day15.hash;
import static de.mvitz.aoc2023.Day15.sumOfHashedInitializationSequenceStepsFor;
import static de.mvitz.aoc2023.Utils.firstLineOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Day15Tests {

	@ParameterizedTest
	@Order(0)
	@CsvSource(textBlock = """
			H,200
			HA,153
			HAS,172
			HASH,52
			rn=1,30
			cm-,253
			qp=3,97
			cm=2,47
			qp-,14
			pc=4,180
			ot=9,9
			ab=5,197
			pc-,48
			pc=6,214
			ot=7,231
			""")
	void part1_hash(String string, long expectedHash) {
		var hash = hash(string);

		assertThat(hash, is(expectedHash));
	}

	@Test
	@Order(1)
	void part1_example() {
		var input = firstLineOf("day15_example.txt");

		var solution = sumOfHashedInitializationSequenceStepsFor(input);

		assertThat(solution, is(1_320L));
	}

	@Test
	@Order(2)
	void part1() {
		var input = firstLineOf("day15.txt");

		var solution = sumOfHashedInitializationSequenceStepsFor(input);

		assertThat(solution, is(506_437L));
	}
}
