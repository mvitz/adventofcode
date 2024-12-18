package de.mvitz.aoc2024.day07;

import de.mvitz.aoc2024.day07.Day07.Equation.Operator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;

public final class Day07 {

	private Day07() {
	}

	public static long totalCalibrationResultFrom(String input, List<Operator> allowedOperators) {
		return input.lines()
				.map(Equation::from)
				.filter(equation -> equation.couldBeTrue(allowedOperators))
				.mapToLong(Equation::testValue)
				.sum();
	}

	record Equation(long testValue, List<Long> remainingNumbers) {

		enum Operator {
			ADD(Long::sum),
			MULTIPLY((first, second) -> first * second),
			CONCATENATION((first, second) -> Long.parseLong(("" + first) + second));

			private final LongBinaryOperator fn;

			Operator(LongBinaryOperator fn) {
				this.fn = fn;
			}

			public long applyTo(long first, long second) {
				return fn.applyAsLong(first, second);
			}
		}

		public boolean couldBeTrue(List<Operator> allowedOperators) {
			var numbers = new ArrayDeque<>(remainingNumbers);

			List<Long> results = new ArrayList<>();
			results.add(numbers.pollFirst());

			Long next;
			while ((next = numbers.pollFirst()) != null) {
				var n = next;
				results = results.stream()
						.flatMap(result -> allowedOperators.stream().map(operator -> operator.applyTo(result, n)))
						.toList();
			}

			return results.contains(testValue);
		}

		public static Equation from(String line) {
			var parts = line.split(": ");
			return new Equation(
					Long.parseLong(parts[0]),
					Arrays.stream(parts[1].split(" "))
							.map(Long::parseLong)
							.toList());
		}
	}
}
