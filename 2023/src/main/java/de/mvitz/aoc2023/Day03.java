package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.mvitz.aoc2023.Utils.isBetween;
import static java.util.Collections.unmodifiableList;

final class Day03 {

	private Day03() {
	}

	static long sumOfPartNumbers(String input) {
		return Engine.from(input)
				.partNumbers().stream()
				.mapToLong(Engine.Number::value)
				.sum();
	}

	static long sumOfGearRatios(String input) {
		return Engine.from(input)
				.gears().stream()
				.mapToLong(Engine.Gear::ratio)
				.sum();
	}

	private static final class Engine {

		private final String[][] schematic;

		private Engine(String[][] schematic) {
			this.schematic = schematic;
		}

		public List<Number> partNumbers() {
			return numbers().stream()
					.filter(this::isPartNumber)
					.toList();
		}

		private List<Number> numbers() {
			var numbers = new ArrayList<Number>();
			Number currentNumber = null;
			for (var cell : cells()) {
				if (cell.isDigit()) {
					if (currentNumber == null) {
						currentNumber = Number.of(cell);
					} else {
						if (currentNumber.isInSameLineThen(cell)) {
							currentNumber = currentNumber.append(cell);
						} else {
							numbers.add(currentNumber);
							currentNumber = null;
						}
					}
				} else if (currentNumber != null) {
					numbers.add(currentNumber);
					currentNumber = null;
				}
			}
			if (currentNumber != null) {
				numbers.add(currentNumber);
			}
			return numbers;
		}

		private List<Cell> cells() {
			var cells = new ArrayList<Cell>();
			for (var y = 0; y < schematic.length; y++) {
				var line = schematic[y];
				for (var x = 0; x < line.length; x++) {
					var point = new Point(x, y);
					cells.add(new Cell(point, valueOf(point).orElseThrow()));
				}
			}
			return cells;
		}

		private Optional<String> valueOf(Point point) {
			if (!point.isInBoundsOf(schematic[0].length - 1, schematic.length - 1)) {
				return Optional.empty();
			}

			return Optional.of(schematic[point.y()][point.x()]);
		}

		private boolean isPartNumber(Number number) {
			return adjacentCellsOf(number).stream()
					.anyMatch(Cell::isSymbol);
		}

		private List<Cell> adjacentCellsOf(Number number) {
			var cells = new ArrayList<Cell>();
			for (var y = number.start().y() - 1; y < number.start().y() + 2; y++) {
				for (var x = number.start().x() - 1; x < number.end().x() + 2; x++) {
					var point = new Point(x, y);
					valueOf(point)
							.map(value -> new Cell(point, value))
							.ifPresent(cells::add);
				}
			}
			return cells;
		}

		public List<Gear> gears() {
			return cells().stream()
					.filter(Cell::isGear)
					.map(cell -> {
						var numbers = adjacentNumbersOf(cell);
						if (numbers.size() != 2) {
							return Optional.<Gear>empty();
						}
						var ratio = numbers.getFirst().value() * numbers.getLast().value();
						return Optional.of(new Gear(cell.point, ratio));
					})
					.filter(Optional::isPresent)
					.map(Optional::get)
					.toList();
		}

		private List<Number> adjacentNumbersOf(Cell cell) {
			return numbers().stream()
					.filter(number -> number.isAdjacentOf(cell.point))
					.toList();
		}

		private static Engine from(String input) {
			return new Engine(input.lines()
					.map(line -> line.split(""))
					.toArray(String[][]::new));
		}

		record Cell(Point point, String value) {

			private static final Set<String> DIGITS = Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

			public boolean isDigit() {
				return DIGITS.contains(value);
			}

			public boolean isSymbol() {
				return !isDigit() && !isEmpty();
			}

			public boolean isEmpty() {
				return ".".equals(value);
			}

			public boolean isGear() {
				return "*".equals(value);
			}
		}

		record Number(List<Cell> cells) {

			public Number append(Cell cell) {
				var cells = new ArrayList<>(this.cells);
				cells.add(cell);

				return new Number(unmodifiableList(cells));
			}

			public long value() {
				return cells.stream()
						.map(Cell::value)
						.mapToLong(Long::parseLong)
						.reduce((a, b) -> (a * 10) + b)
						.orElse(0L);
			}

			public boolean isInSameLineThen(Cell cell) {
				return start().inSameRowAs(cell.point);
			}

			public Point start() {
				return cells.getFirst().point;
			}

			public Point end() {
				return cells.getLast().point;
			}

			public boolean isAdjacentOf(Point point) {
				var start = cells.getFirst().point;
				var end = cells.getLast().point;
				return isBetween(point.y(), start.y() - 2, start.y() + 2)
					   && isBetween(point.x(), start.x() - 2, end.x() + 2);
			}

			public static Number of(Cell cell) {
				return new Number(List.of(cell));
			}
		}

		record Gear(Point point, long ratio) {
		}
	}
}
