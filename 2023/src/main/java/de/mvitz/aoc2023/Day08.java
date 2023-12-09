package de.mvitz.aoc2023;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day08 {

	private Day08() {
	}

	static long numberOfStepsToReachTarget(String input) {
		var directions = parseDirections(input);
		var start = parseMap(input).get("AAA");

		return numberOfStepsUntil(start, directions, node -> node.name.equals("ZZZ"));
	}

	static long numberOfGhostStepsToReachTarget(String input) {
		var nodes = parseMap(input)
				.values().stream()
				.filter(node -> node.name.endsWith("A"))
				.collect(Collectors.toSet());

		var steps = nodes.stream()
				.map(node -> {
					var directions = parseDirections(input);
					return numberOfStepsUntil(node, directions, n -> n.name.endsWith("Z"));
				})
				.collect(Collectors.toSet());

		return steps.stream().reduce(Day08::lcm).orElseThrow();
	}

	private static long numberOfStepsUntil(Node start, Iterator<Direction> directions, Predicate<Node> predicate) {
		var node = start;

		var steps = 0;
		while (!predicate.test(node)) {
			node = directions.next().apply(node);
			steps++;
		}

		return steps;
	}

	private static Iterator<Direction> parseDirections(String input) {
		return new RepeatListIterator<>(Arrays.stream(input.lines()
						.findFirst()
						.orElseThrow()
						.split(""))
				.map(Direction::valueOf)
				.toList());
	}

	private static final Pattern NODE_PATTERN = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

	private static Map<String, Node> parseMap(String input) {
		var map = new HashMap<String, Node>();
		input.lines()
				.skip(2)
				.map(NODE_PATTERN::matcher)
				.filter(Matcher::matches)
				.forEach(line -> {
					var node = map.computeIfAbsent(line.group(1), Node::new);
					node.left = map.computeIfAbsent(line.group(2), Node::new);
					node.right = map.computeIfAbsent(line.group(3), Node::new);
				});
		return map;
	}

	private static long gcd(long first, long second) {
		if (first == 0 || second == 0) {
			return first + second;
		}
		var big = Math.max(Math.abs(first), Math.abs(second));
		var small = Math.min(Math.abs(first), Math.abs(second));
		return gcd(big % small, small);
	}

	public static long lcm(long first, long second) {
		if (first == 0 || second == 0) {
			return 0;
		}
		var gcd = gcd(first, second);
		return Math.abs(first * second) / gcd;
	}

	private static final class Node {

		private final String name;

		private Node left;
		private Node right;

		public Node(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return STR."\{name} = (\{left.name}, \{right.name})";
		}
	}

	private enum Direction {
		L, R;

		public Node apply(Node node) {
			return switch (this) {
				case L -> node.left;
				case R -> node.right;
			};
		}
	}

	public static final class RepeatListIterator<T> implements Iterator<T> {

		private final List<T> values;
		private Iterator<T> current;

		public RepeatListIterator(List<T> values) {
			this.values = values;
			current = this.values.iterator();
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public T next() {
			if (!current.hasNext()) {
				current = values.iterator();
			}
			return current.next();
		}
	}
}
