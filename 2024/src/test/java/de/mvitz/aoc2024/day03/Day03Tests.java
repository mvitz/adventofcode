package de.mvitz.aoc2024.day03;

import de.mvitz.aoc2024.utils.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2024.day03.Day03.findSumOfValidEnabledMultiplicationsFrom;
import static de.mvitz.aoc2024.day03.Day03.findSumOfValidMultiplicationsFrom;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Tests {

    @Test
    @Order(0)
    void part1_example() {
        // given
        var input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

        // when
        var solution = findSumOfValidMultiplicationsFrom(input);

        // then
        assertThat(solution)
                .isEqualTo(161);
    }

    @Test
    @Order(1)
    void part1() {
        // given
        var input = Files.contentOf("day03/input.txt");

        // when
        var solution = findSumOfValidMultiplicationsFrom(input);

        // then
        assertThat(solution)
                .isEqualTo(173_529_487);
    }

	@Test
	@Order(2)
	void part2_example() {
		// given
		var input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

		// when
		var solution = findSumOfValidEnabledMultiplicationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(48);
	}

	@Test
	@Order(3)
	void part2() {
		// given
		var input = Files.contentOf("day03/input.txt");

		// when
		var solution = findSumOfValidEnabledMultiplicationsFrom(input);

		// then
		assertThat(solution)
				.isEqualTo(99_532_691);
	}
}
