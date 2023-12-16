package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Day12.ConditionRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static de.mvitz.aoc2023.Day12.sumOfArrangementsFor;
import static de.mvitz.aoc2023.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day12Tests {

	@ParameterizedTest
	@ValueSource(strings = {
			"#.#.### 1,1,3",
			".#...#....###. 1,1,3",
			".#.###.#.###### 1,3,1,6",
			"####.#...#... 4,1,1",
			"#....######..#####. 1,6,5",
			".###.##....# 3,2,1"
	})
	@Order(0)
	void conditionRecord_shouldDetectValidRecords(String input) {
		var record = ConditionRecord.from(input);

		assertThat(record.isValid(), is(true));
	}

	@Test
	@Order(1)
	void part1_example() {
		var input = contentOf("day12_example.txt");

		var solution = sumOfArrangementsFor(input);

		assertThat(solution, is(21L));
	}

	@Disabled("Takes to long to run regularly")
	@Test
	@Order(2)
	void part1() {
		var input = contentOf("day12.txt");

		var solution = sumOfArrangementsFor(input);

		assertThat(solution, is(8_180L));
	}
}
