package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

final class Day06 {

	private Day06() {
	}

	static long productOfRecordBeatingWaysFor(String input) {
		return parse(input).stream()
				.mapToLong(Race::numberOfWaysToBeatRecord)
				.reduce((i, j) -> i * j)
				.orElseThrow();
	}

	private static List<Race> parse(String input) {
		var lines = input.lines().toList();

		var times = lines.getFirst().substring("Time:".length()).strip().split("\\s+");
		var distances = lines.getLast().substring("Distance:".length()).strip().split("\\s+");

		var races = new ArrayList<Race>();
		for (var i = 0; i < times.length; i++) {
			races.add(new Race(Long.parseLong(times[i]), Long.parseLong(distances[i])));
		}
		return races;
	}

	record Race(long time, long recordDistance) {

		public long numberOfWaysToBeatRecord() {
			return LongStream.iterate(0, i -> i < time, i -> i + 1)
					.map(buttonHoldingTime -> buttonHoldingTime * (time - buttonHoldingTime))
					.filter(distance -> distance > recordDistance)
					.count();
		}
	}
}
