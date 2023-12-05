package de.mvitz.aoc2023;

import java.util.*;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

final class Day05 {

	private Day05() {
	}

	static long lowestLocationNumberValues(String input) {
		return Almanac.from(input, Almanac.Seeds::valuesFrom)
				.findLowestLocationNumber();
	}

	static long lowestLocationNumberRanges(String input) {
		return Almanac.from(input, Almanac.Seeds::rangesFrom)
				.findLowestLocationNumber();
	}

	record Range(long lower, long upper) {

		public boolean contains(long number) {
			return number >= lower && number <= upper;
		}

		static Range from(long start, long length) {
			return new Range(start, start + length - 1);
		}

		static Range of(long number) {
			return new Range(number, number);
		}
	}

	record Almanac(Seeds seeds, List<TransformationMap> maps) {

		public long findLowestLocationNumber() {
			return LongStream.iterate(0, location -> location + 1)
					.parallel()
					.filter(this::isValidLocation)
					.findFirst()
					.orElseThrow();
		}

		private boolean isValidLocation(long number) {
			for (var map : maps) {
				number = map.convert(number);
			}
			return seeds.contains(number);
		}

		public static Almanac from(String input, Function<Iterator<String>, Seeds> seedsParser) {
			var lines = input.lines().iterator();

			var seeds = seedsParser.apply(lines);

			var maps = new ArrayList<TransformationMap>();
			while (lines.hasNext()) {
				maps.add(TransformationMap.from(lines));
			}

			return new Almanac(seeds, maps.reversed());
		}

		static final class Seeds {

			private final List<Range> values;

			private Seeds(List<Range> values) {
				this.values = values;
			}

			public boolean contains(long number) {
				for (var value : values) {
					if (value.contains(number)) {
						return true;
					}
				}
				return false;
			}

			public static Seeds valuesFrom(Iterator<String> lines) {
				var line = lines.next();
				lines.next(); // skip empty line

				return new Seeds(Arrays.stream(line.substring("seeds: ".length()).split(" "))
						.map(String::strip)
						.map(Long::parseLong)
						.map(Range::of)
						.toList());
			}

			public static Seeds rangesFrom(Iterator<String> lines) {
				var values = valuesFrom(lines).values;

				return new Seeds(Stream.iterate(0, i -> i < values.size(), i -> i + 2)
						.map(i -> Range.from(values.get(i).lower, values.get(i + 1).lower))
						.toList());
			}
		}

		static final class TransformationMap {

			private final Map<Range, Long> entries = new HashMap<>();

			private TransformationMap() {
			}

			private void put(Range key, long change) {
				entries.put(key, change);
			}

			public long convert(long number) {
				return entries.entrySet().stream()
						.filter(entry -> entry.getKey().contains(number))
						.map(Map.Entry::getValue)
						.mapToLong(value -> number + value)
						.findFirst()
						.orElse(number);
			}

			public static TransformationMap from(Iterator<String> lines) {
				var map = new TransformationMap();

				lines.next(); // skip heading

				while (lines.hasNext()) {
					var line = lines.next();

					if (line.isBlank()) {
						return map;
					}

					var entry = line.split(" ");

					var destination = Long.parseLong(entry[0]);
					var source = Long.parseLong(entry[1]);
					var range = Long.parseLong(entry[2]);

					map.put(Range.from(destination, range), source - destination);
				}

				return map;
			}
		}
	}
}
