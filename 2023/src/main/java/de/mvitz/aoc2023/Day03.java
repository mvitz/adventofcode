package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

final class Day03 {

	private Day03() {
	}

	static long sumOfPartNumbers(String input) {
		var engine = new Engine(input.lines()
				.map(line -> line.split(""))
				.toArray(String[][]::new));
		return engine.partNumbers().sum();
	}

	@SuppressWarnings("java:S6218")
	record Engine(String[][] schematic) {

		private static final Set<String> DIGITS = Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		LongStream partNumbers() {
			return numbers()
					.filter(this::isPartNumber)
					.map(Number::value)
					.mapToLong(Long::parseLong);
		}

		private Stream<Number> numbers() {
			List<Number> numbers = new ArrayList<>();
			Number currentNumber = null;
			for (var y = 0; y < schematic.length; y++) {
				var line = schematic[y];
				for (var x = 0; x < line.length; x++) {
					var cell = valueOf(x, y);
					if (DIGITS.contains(cell)) {
						if (currentNumber == null) {
							currentNumber = new Number(cell, new Point(x, y));
						} else {
							currentNumber = new Number(currentNumber.value + cell, currentNumber.start);
						}
					} else if (currentNumber != null) {
						numbers.add(currentNumber);
						currentNumber = null;
					}
				}
				if (currentNumber != null) {
					numbers.add(currentNumber);
					currentNumber = null;
				}
			}
			return numbers.stream();
		}

		private boolean isPartNumber(Number number) {
			return adjacentCellsOf(number)
					.filter(not("."::equals))
					.anyMatch(not(DIGITS::contains));
		}

		private Stream<String> adjacentCellsOf(Number number) {
			List<String> cells = new ArrayList<>();
			for (var yd = -1; yd < 2; yd++) {
				for (var xd = -1; xd < (1 + number.value.length()); xd++) {
					cells.add(valueOf(number.start.x + xd, number.start.y + yd));
				}
			}
			return cells.stream().filter(not(String::isBlank));
		}

		private String valueOf(int x, int y) {
			if (y >= schematic.length || y < 0) {
				return "";
			}
			var line = schematic[y];
			if (x >= line.length || x < 0) {
				return "";
			}
			return line[x];
		}

		record Number(String value, Point start) {
		}

		record Point(int x, int y) {
		}
	}
}
