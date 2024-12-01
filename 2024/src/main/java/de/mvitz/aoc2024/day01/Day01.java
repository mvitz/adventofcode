package de.mvitz.aoc2024.day01;

import de.mvitz.aoc2024.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day01 {

	public static long findTotalDistanceBetweenLists(String input) {
		var lists = parse(input);

		var left = lists.left().stream()
				.sorted()
				.toList();
		var right = lists.right().stream()
				.sorted()
				.toList();

		return totalDistanceBetween(left, right);
	}

	private static Pair<List<Integer>, List<Integer>> parse(String input) {
		return input.lines()
				.map(Day01::parseLine)
				.collect(
						() -> Pair.of(new ArrayList<Integer>(), new ArrayList<Integer>()),
						(acc, element) -> {
							acc.left().add(element.left());
							acc.right().add(element.right());
						},
						(left, right) -> {
							left.left().addAll(right.left());
							left.right().addAll(right.right());
						});
	}

	private static Pair<Integer, Integer> parseLine(String line) {
		var parts = line.split(" {3}");
		return Pair.of(
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]));
	}

	private static long totalDistanceBetween(List<Integer> left, List<Integer> right) {
		long totalDistance = 0;
		for (int i = 0; i < left.size(); i++) {
			var distance = distanceBetween(left.get(i), right.get(i));
			totalDistance += distance;
		}
		return totalDistance;
	}

	private static int distanceBetween(int left, int right) {
		return Math.abs(left - right);
	}
}
