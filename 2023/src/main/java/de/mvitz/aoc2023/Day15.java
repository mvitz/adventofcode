
package de.mvitz.aoc2023;

import de.mvitz.aoc2023.Day15.Box.Lens;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

final class Day15 {

	private Day15() {
	}

	static int sumOfHashedInitializationSequenceStepsFor(String input) {
		return stepsFrom(input)
				.mapToInt(Day15::hash)
				.sum();
	}

	static int focusingPowerOfResultingLensConfigurationFor(String input) {
		var boxes = new HashMap<Integer, Box>();
		stepsFrom(input)
				.map(Operation::parse)
				.forEach(op -> op.applyTo(boxes));

		return boxes.values().stream()
				.mapToInt(Box::power)
				.sum();
	}

	static Stream<String> stepsFrom(String input) {
		return Arrays.stream(input.split(","));
	}

	static int hash(String string) {
		return string.chars()
				.reduce(0, (value, character) -> ((value + character) * 17) % 256);
	}

	sealed interface Operation {

		String label();

		default void applyTo(Map<Integer, Box> boxes) {
			var box = boxes.computeIfAbsent(
					hash(label()), Box::empty);
			switch (this) {
				case Removal(String label) -> box.remove(label);
				case Installation(Lens lens) -> box.install(lens);
			}
		}

		static Operation parse(String input) {
			if (input.endsWith("-")) {
				return new Removal(input.substring(0, input.length() - 1));
			} else {
				var parts = input.split("=");
				return new Installation(new Lens(parts[0], Integer.parseInt(parts[1])));
			}
		}

		record Removal(String label) implements Operation {
		}

		record Installation(Lens lens) implements Operation {

			@Override
			public String label() {
				return lens.label;
			}
		}
	}

	record Box(int number, List<Lens> lenses) {

		public void install(Lens lens) {
			lenses.stream()
					.filter(l -> l.label.equals(lens.label))
					.findFirst()
					.ifPresentOrElse(
							l -> l.setFocalLength(lens.focalLength),
							() -> lenses.add(lens));
		}

		public void remove(String label) {
			lenses.removeIf(lens -> lens.label.equals(label));
		}

		public int power() {
			var power = 0;
			for (var i = 0; i < lenses.size(); i++) {
				power += lenses.get(i).power(number, i);
			}
			return power;
		}

		@Override
		public String toString() {
			return "Box " + number + ": " + lenses.stream().map(Lens::toString).collect(joining(" "));
		}

		public static Box empty(int number) {
			return new Box(number, new ArrayList<>());
		}

		static class Lens {

			private final String label;
			private int focalLength;

			public Lens(String label, int focalLength) {
				this.label = label;
				this.focalLength = focalLength;
			}

			public void setFocalLength(int focalLength) {
				this.focalLength = focalLength;
			}

			public int power(int boxNumber, int slotNumber) {
				return (boxNumber + 1) * (slotNumber + 1) * focalLength;
			}

			@Override
			public String toString() {
				return "[" +  label + " " + focalLength + "]";
			}
		}
	}
}
