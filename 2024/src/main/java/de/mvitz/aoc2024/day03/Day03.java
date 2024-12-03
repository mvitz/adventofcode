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
		var m = MUL_PATTERN.matcher(line);
		while (m.find()) {
			multiplications.add(Pair.of(
					Long.parseLong(m.group(1)),
					Long.parseLong(m.group(2))));
		}
		return multiplications;
	}
}
