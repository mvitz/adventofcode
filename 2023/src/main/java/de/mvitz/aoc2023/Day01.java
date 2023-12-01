package de.mvitz.aoc2023;

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
}
