package de.mvitz.aoc2024.day02;

import java.util.List;

public final class Reports {

	private final List<Report> values;

	private Reports(List<Report> values) {
		this.values = values;
	}

	public long numberOfSafeReports() {
		return values.stream()
				.filter(Report::isSafe)
				.count();
	}

	public long numberOfToleratedSafeReports() {
		return values.stream()
				.filter(Report::isToleratedSafe)
				.count();
	}

	public static Reports from(String input) {
		return new Reports(input.lines()
				.map(Report::from)
				.toList());
	}
}
