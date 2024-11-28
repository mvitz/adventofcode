
package de.mvitz.aoc2023;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

final class Day19 {

	private Day19() {
	}

	static long sumOfAllAcceptedParts(String input) {
		var workflowsAndParts = input.split("\n\n");

		var workflows = Workflows.from(workflowsAndParts[0]);
		return workflowsAndParts[1].lines()
				.map(Part::from)
				.filter(workflows::process)
				.mapToLong(Part::rating)
				.sum();
	}

	static class Workflows {

		private final Map<String, Workflow> workflows;

		private Workflows(Map<String, Workflow> workflows) {
			this.workflows = workflows;
		}

		public boolean process(Part part) {
			var workflow = "in";

			while (true) {
				if ("A".equals(workflow)) {
					return true;
				} else if ("R".equals(workflow)) {
					return false;
				} else {
					workflow = run(workflow, part);
				}
			}
		}

		private String run(String workflowName, Part part) {
			var workflow = workflows.get(workflowName);
			return workflow.process(part);
		}

		public static Workflows from(String input) {
			return new Workflows(
					input.lines()
							.map(Workflow::from)
							.collect(Collectors.toMap(Workflow::name, identity())));
		}

		static class Workflow {

			private final String name;
			private final Rules rules;

			private Workflow(String name, Rules rules) {
				this.name = name;
				this.rules = rules;
			}

			public String name() {
				return this.name;
			}

			public String process(Part part) {
				return rules.apply(part);
			}

			public static Workflow from(String line) {
				return new Workflow(
						line.substring(0, line.indexOf('{')),
						Rules.from(line.substring(line.indexOf('{') + 1, line.length() - 1)));
			}

			private static class Rules {

				private final List<Rule> rules;

				private Rules(List<Rule> rules) {
					this.rules = rules;
				}

				public String apply(Part part) {
					return rules.stream()
							.filter(rule -> rule.matches(part))
							.findFirst()
							.orElseThrow()
							.apply();
				}

				public static Rules from(String input) {
					return new Rules(
							Arrays.stream(input.split(","))
									.map(Rule::from)
									.toList());
				}

				private static class Rule {

					private static final Pattern PATTERN = Pattern.compile("([xmas])([<>])(\\d+):(\\w+)");

					private final Predicate<Part> matcher;
					private final String nextWorkflow;

					private Rule(Predicate<Part> matcher, String nextWorkflow) {
						this.matcher = matcher;
						this.nextWorkflow = nextWorkflow;
					}

					public boolean matches(Part part) {
						return matcher.test(part);
					}

					public String apply() {
						return nextWorkflow;
					}

					public static Rule from(String input) {
						if (!input.contains(":")) {
							return new Rule(_ -> true, input);
						}

						var m = PATTERN.matcher(input);
						if (!m.matches()) {
							throw new IllegalArgumentException("Unparsable rule: " + input);
						}

						var category = m.group(1);
						var comparison = m.group(2);
						var value = Long.parseLong(m.group(3));

						Predicate<Part> matcher = switch (comparison) {
							case "<" -> part -> part.get(category) < value;
							case ">" -> part -> part.get(category) > value;
							default ->
									throw new IllegalStateException("Unsupported comparison: " + comparison);
						};

						return new Rule(matcher, m.group(4));
					}
				}
			}
		}
	}

	static class Part {

		private static final Pattern PATTERN = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");

		private final long x;
		private final long m;
		private final long a;
		private final long s;

		private Part(long x, long m, long a, long s) {
			this.x = x;
			this.m = m;
			this.a = a;
			this.s = s;
		}

		public long get(String category) {
			return switch (category) {
				case "x" -> x;
				case "m" -> m;
				case "a" -> a;
				case "s" -> s;
				default ->
						throw new IllegalArgumentException("Unknown category: " + category);
			};
		}

		public long rating() {
			return x + m + a + s;
		}

		public static Part from(String line) {
			var m = PATTERN.matcher(line);
			if (!m.matches()) {
				throw new IllegalArgumentException("Unparsable part: " + line);
			}
			return new Part(
					Long.parseLong(m.group(1)),
					Long.parseLong(m.group(2)),
					Long.parseLong(m.group(3)),
					Long.parseLong(m.group(4)));
		}
	}
}
