package de.mvitz.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Day08 {

	private Day08() {
	}

	static long numberOfStepsToReachTarget(String input) {
		var directions = parseDirections(input);
		var map = parseMap(input);

		var steps = 0;
		while (!map.name.equals("ZZZ")) {
			map = directions.next().apply(map);
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

	private static final Pattern NODE_PATTERN = Pattern.compile("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)");

	private static Node parseMap(String input) {
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
		return map.get("AAA");
	}

	private static final class Node {

		private final String name;

		private Node left;
		private Node right;

		public Node(String name) {
			this.name = name;
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
