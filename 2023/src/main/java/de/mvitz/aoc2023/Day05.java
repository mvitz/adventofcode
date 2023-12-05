package de.mvitz.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.Comparator.naturalOrder;

final class Day05 {

	private Day05() {
	}

	static long lowestLocationNumber(String input) {
		var it = input.lines().iterator();

		var seeds = new ArrayList<>(parseSeeds(it.next()));
		it.next(); // skip empty line

		var locations = findLocationsFor(seeds, it);

		return locations.stream().min(naturalOrder()).orElseThrow();
	}

	private static List<Long> parseSeeds(String line) {
		return Arrays.stream(line.substring("seeds: ".length()).split(" "))
				.map(String::strip)
				.map(Long::parseLong)
				.toList();
	}

	private static List<Long> findLocationsFor(List<Long> input, Iterator<String> lines) {
		var out = new ArrayList<Long>();

		lines.next(); // skip heading
		while (lines.hasNext()) {
			var line = lines.next();
			if (line.isBlank()) {
				break;
			}

			var entry = line.split(" ");
			var destination = Long.parseLong(entry[0]);
			var source = Long.parseLong(entry[1]);
			var range = Long.parseLong(entry[2]);

			var it = input.iterator();
			while (it.hasNext()) {
				var number = it.next();
				if (number >= source && number < (source + range)) {
					it.remove();
					out.add(destination + number - source);
				}
			}
		}

		out.addAll(input);

		if (lines.hasNext()) {
			return findLocationsFor(out, lines);
		}
		return out;
	}
}
