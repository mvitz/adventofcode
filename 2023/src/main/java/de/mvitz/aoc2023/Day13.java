package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

final class Day13 {

	private Day13() {
	}

	static long sumOfNotesFor(String input) {
		return Arrays.stream(input.split("\n\n"))
				.map(Pattern::from)
				.mapToLong(Pattern::note)
				.sum();
	}

	record Pattern(List<String> rows, List<String> columns) {

		public long note() {
			return reflectsAt(columns) + 100 * reflectsAt(rows);
		}

		public static Pattern from(String input) {
			var rows = input.lines().filter(not(String::isBlank)).toList();
			var columns = new ArrayList<>(Stream.generate(() -> "").limit(rows.getFirst().length()).toList());
			rows.forEach(row -> {
				for (int i = 0; i < row.length(); i++) {
					var character = row.substring(i, i + 1);
					columns.set(i, columns.get(i) + character);
				}
			});
			return new Pattern(rows, columns);
		}

		private static long reflectsAt(List<String> lines) {
			for (var i = 0; i < lines.size() - 1; i++) {
				if (hasReflectionAt(lines, i)) {
					return i + 1L;
				}
			}
			return 0;
		}

		private static boolean hasReflectionAt(List<String> lines, int reflectAt) {
			for (var i = 0; i <= reflectAt && (reflectAt + i + 1) < lines.size(); i++) {
				var left = lines.get(reflectAt - i);
				var right = lines.get(reflectAt + 1 + i);

				if (!left.equals(right)) {
					return false;
				}
			}
			return true;
		}
	}
}
