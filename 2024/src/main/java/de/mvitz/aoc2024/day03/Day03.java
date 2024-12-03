package de.mvitz.aoc2024.day03;

import de.mvitz.aoc2024.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Day03 {

	private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

	private Day03() {
	}

	public static long findSumOfValidMultiplicationsFrom(String input) {
		return multiplicationsFrom(input).stream()
				.mapToLong(pair -> pair.left() * pair.right())
				.sum();
	}

	private static List<Pair<Long, Long>> multiplicationsFrom(String line) {
		var multiplications = new ArrayList<Pair<Long, Long>>();

		int start = 0, end = 0;
		while (end < line.length()) {
			start = line.indexOf("mul(", end);
			if (start < 0) {
				return multiplications;
			}

			end = line.indexOf(')', start);
			if (end < 0) {
				return multiplications;
			}

			var candidate = line.substring(start, end + 1);
			var m = MUL_PATTERN.matcher(candidate);
			if (m.matches()) {
				multiplications.add(Pair.of(
						Long.parseLong(m.group(1)),
						Long.parseLong(m.group(2))));
			} else {
				end = start + 1;
			}
		}
		return multiplications;
	}
}
