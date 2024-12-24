package de.mvitz.aoc2024.day11;

import de.mvitz.aoc2024.utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

public final class Day11 {

	private Day11() {
	}

	public static long stonesAfterIterationsFrom(String input, int iterations) {
		return stream(input.split(" "))
				.mapToLong(engraving -> blink(iterations, engraving))
				.sum();
	}

	private static final Map<Pair<Integer, String>, Long> CACHE = new HashMap<>();

	private static long blink(int times, String engraving) {
		if (times == 0) {
			return 1;
		}

		var cacheKey = Pair.of(times, engraving);
		if (CACHE.containsKey(cacheKey)) {
			return CACHE.get(cacheKey);
		}

		var result = switch (engraving) {
			case "0" -> blink(times - 1, "1");
			case String s when (s.length() % 2 == 0) -> {
				var stones = split(engraving);
				yield blink(times - 1, stones[0]) + blink(times - 1, stones[1]);
			}
			default ->
					blink(times - 1, Long.toString(Long.parseLong(engraving) * 2024));
		};

		CACHE.put(cacheKey, result);

		return result;
	}

	private static String[] split(String stone) {
		var middle = stone.length() / 2;
		return new String[]{
				Long.toString(Long.parseLong(stone.substring(0, middle))),
				Long.toString(Long.parseLong(stone.substring(middle)))
		};
	}
}
