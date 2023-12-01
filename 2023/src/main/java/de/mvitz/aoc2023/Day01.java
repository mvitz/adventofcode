package de.mvitz.aoc2023;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

final class Day01 {

	private Day01() {
	}

	static long sumOfCalibrationValues(String input) {
		return input.lines()
				.mapToLong(Day01::extractCalibrationValue)
				.sum();
	}

	private static final Pattern CALIBRATION_VALUE_PATTERN = Pattern.compile("\\D*(\\d).*?(\\d?)\\D*");

	private static long extractCalibrationValue(String line) {
		var m = CALIBRATION_VALUE_PATTERN.matcher(line);
		if (!m.matches()) {
			return 0;
		}
		var firstDigit = m.group(1);
		var secondDigit = m.group(2);
		return Long.parseLong(firstDigit + (secondDigit.isBlank() ? firstDigit : secondDigit));
	}

	static long sumOfRealCalibrationValues(String input) {
		return input.lines()
				.mapToLong(Day01::extractRealCalibrationValue)
				.sum();
	}

	private static long extractRealCalibrationValue(String line) {
		return Long.parseLong(findFirstDigit(line) + findLastDigit(line));
	}

	private static final Map<String, String> DIGITS = Map.ofEntries(
			Map.entry("0", "0"),
			Map.entry("1", "1"),
			Map.entry("2", "2"),
			Map.entry("3", "3"),
			Map.entry("4", "4"),
			Map.entry("5", "5"),
			Map.entry("6", "6"),
			Map.entry("7", "7"),
			Map.entry("8", "8"),
			Map.entry("9", "9"),
			Map.entry("zero", "0"),
			Map.entry("one", "1"),
			Map.entry("two", "2"),
			Map.entry("three", "3"),
			Map.entry("four", "4"),
			Map.entry("five", "5"),
			Map.entry("six", "6"),
			Map.entry("seven", "7"),
			Map.entry("eight", "8"),
			Map.entry("nine", "9"));

	private static String findFirstDigit(String line) {
		return findDigit(line::indexOf, Integer.MAX_VALUE, (i, index) -> i < index);
	}

	private static String findLastDigit(String line) {
		return findDigit(line::lastIndexOf, -1, (i, index) -> i > index);
	}

	private static String findDigit(
			ToIntFunction<String> findIndex,
			int startingIndex,
			BiPredicate<Integer, Integer> check) {
		var digit = "";
		var index = startingIndex;
		for (var e : DIGITS.entrySet()) {
			var i = findIndex.applyAsInt(e.getKey());
			if (i > -1 && check.test(i, index)) {
				index = i;
				digit = e.getValue();
			}
		}
		return digit;
	}
}
