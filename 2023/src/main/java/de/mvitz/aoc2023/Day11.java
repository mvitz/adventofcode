package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class Day11 {

	private Day11() {
	}

	static long sumOfAllShortestPathsBetweenGalaxyPairs(String input) {
		return sumOfAllShortestPathsBetweenOlderGalaxyPairs(input, 2);
	}

	static long sumOfAllShortestPathsBetweenOlderGalaxyPairs(String input, long factor) {
		return Universe.parse(input)
				.expand(factor - 1)
				.galaxyPairs().stream()
				.mapToLong(Pair::shortestPathBetween)
				.sum();
	}

	record Point(long x, long y) {
	}

	record Pair(Point first, Point second) {

		long shortestPathBetween() {
			return Math.abs(first.x - second.x) + Math.abs(first.y - second.y);
		}
	}

	record Universe(List<Point> galaxies) {

		Universe expand(long factor) {
			return new Universe(shiftColumns(shiftRows(galaxies, factor), factor));
		}

		Set<Pair> galaxyPairs() {
			var pairs = new HashSet<Pair>();

			for (var i = 0; i < galaxies.size(); i++) {
				var first = galaxies.get(i);
				for (var j = i + 1; j < galaxies.size(); j++) {
					pairs.add(new Pair(first, galaxies.get(j)));
				}
			}

			return pairs;
		}

		static Universe parse(String input) {
			var galaxies = new ArrayList<Point>();

			var lines = input.lines().toList();
			for (var y = 0; y < lines.size(); y++) {
				var columns = lines.get(y).split("");
				for (var x = 0; x < columns.length; x++) {
					if ("#".equals(columns[x])) {
						galaxies.add(new Point(x, y));
					}
				}
			}

			return new Universe(galaxies);
		}

		private static List<Point> shiftRows(List<Point> galaxies, long factor) {
			var shiftedGalaxies = new ArrayList<Point>();

			var shift = 0L;
			for (var row = 0; row < rows(galaxies); row++) {
				var galaxiesInRow = galaxiesInRow(galaxies, row);
				if (galaxiesInRow.isEmpty()) {
					shift += factor;
				} else {
					for (var galaxy : galaxiesInRow) {
						shiftedGalaxies.add(new Point(galaxy.x, galaxy.y + shift));
					}
				}
			}

			return shiftedGalaxies;
		}

		private static long rows(List<Point> galaxies) {
			return galaxies.stream()
						   .mapToLong(Point::y)
						   .max()
						   .orElse(-1) + 1;
		}

		private static List<Point> galaxiesInRow(List<Point> galaxies, long row) {
			return galaxies.stream()
					.filter(galaxy -> galaxy.y == row)
					.toList();
		}

		private static List<Point> shiftColumns(List<Point> galaxies, long factor) {
			var shiftedGalaxies = new ArrayList<Point>();

			var shift = 0L;
			for (var column = 0; column < columns(galaxies); column++) {
				var galaxiesInColumn = galaxiesInColumn(galaxies, column);
				if (galaxiesInColumn.isEmpty()) {
					shift += factor;
				} else {
					for (var galaxy : galaxiesInColumn) {
						shiftedGalaxies.add(new Point(galaxy.x + shift, galaxy.y));
					}
				}
			}

			return shiftedGalaxies;
		}

		private static long columns(List<Point> galaxies) {
			return galaxies.stream()
						   .mapToLong(Point::x)
						   .max()
						   .orElse(-1) + 1;
		}

		private static List<Point> galaxiesInColumn(List<Point> galaxies, long column) {
			return galaxies.stream()
					.filter(galaxy -> galaxy.x == column)
					.toList();
		}
	}
}
