package de.mvitz.aoc2024.day05;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day05.Day05.sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates;
import static de.mvitz.aoc2024.day05.Day05.sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering;
import static org.assertj.core.api.Assertions.assertThat;

class Day05Tests {

	@Test
	@Order(0)
	void part1_example() {
		// given
		var input = """
				47|53
				97|13
				97|61
				97|47
				75|29
				61|13
				75|53
				29|13
				97|29
				53|29
				61|53
				97|53
				61|29
				47|13
				75|47
				97|75
				47|61
				75|61
				47|29
				75|13
				53|13

				75,47,61,53,29
				97,61,53,29,13
				75,29,13
				75,97,47,61,53
				61,13,29
				97,13,75,29,47
				""";

		// when
		var solution = sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates(input);

		// then
		assertThat(solution)
				.isEqualTo(143);
	}

	@Test
	@Order(1)
	void part1() {
		// given
		var input = Files.contentOf("day05/input.txt");

		// when
		var solution = sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates(input);

		// then
		assertThat(solution)
				.isEqualTo(6_034);
	}

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = """
				47|53
				97|13
				97|61
				97|47
				75|29
				61|13
				75|53
				29|13
				97|29
				53|29
				61|53
				97|53
				61|29
				47|13
				75|47
				97|75
				47|61
				75|61
				47|29
				75|13
				53|13

				75,47,61,53,29
				97,61,53,29,13
				75,29,13
				75,97,47,61,53
				61,13,29
				97,13,75,29,47
				""";

		// when
		var solution = sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering(input);

		// then
		assertThat(solution)
				.isEqualTo(123);
	}

	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day05/input.txt");

		// when
		var solution = sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering(input);

		// then
		assertThat(solution)
				.isEqualTo(6_305);
	}
}
