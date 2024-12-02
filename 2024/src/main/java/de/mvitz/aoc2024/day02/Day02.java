package de.mvitz.aoc2024.day02;

public final class Day02 {

	private Day02() {
	}

	public static long findNumberOfSafeReportsFrom(String input) {
		return Reports.from(input)
				.numberOfSaveReports();
	}
}
