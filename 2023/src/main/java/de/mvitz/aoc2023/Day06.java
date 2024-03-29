package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

final class Day06 {

	private Day06() {
	}

	static long productOfRecordBeatingWaysFor(String input) {
		return racesFrom(input).stream()
				.mapToLong(Race::numberOfWaysToBeatRecord)
				.reduce((i, j) -> i * j)
				.orElseThrow();
	}

	static long numberOfRecordingBeatingWaysFor(String input) {
		return raceFrom(input)
				.numberOfWaysToBeatRecord();
	}

	private static List<Race> racesFrom(String input) {
		var lines = input.lines().toList();

		var times = lines.getFirst().substring("Time:".length()).strip().split("\\s+");
		var distances = lines.getLast().substring("Distance:".length()).strip().split("\\s+");

		var races = new ArrayList<Race>();
		for (var i = 0; i < times.length; i++) {
			races.add(new Race(Long.parseLong(times[i]), Long.parseLong(distances[i])));
		}
		return races;
	}

	private static Race raceFrom(String input) {
		var lines = input.lines().toList();

		return new Race(
				Long.parseLong(lines.getFirst().substring("Time:".length()).replaceAll("\\s+", "")),
				Long.parseLong(lines.getLast().substring("Distance:".length()).replaceAll("\\s+", "")));
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
