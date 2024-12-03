package de.mvitz.aoc2024.day03;

import java.util.regex.Pattern;

public final class Day03 {

	private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|(do(n't)*)\\(\\)");

	private interface TokenCallback {
		default void onMul(long first, long second) {
		}

		default void onDo() {
		}

		default void onDont() {
		}
	}

	private Day03() {
	}

	public static long findSumOfValidMultiplicationsFrom(String input) {
		return parse(input, new TokenCallback() {
			long result = 0;

			@Override
			public void onMul(long first, long second) {
				result += first * second;
			}
		}).result;
	}

	public static long findSumOfValidEnabledMultiplicationsFrom(String input) {
		return parse(input, new TokenCallback() {
			boolean enabled = true;
			long result = 0;

			@Override
			public void onDo() {
				enabled = true;
			}

			@Override
			public void onDont() {
				enabled = false;
			}

			@Override
			public void onMul(long first, long second) {
				if (enabled) {
					result += first * second;
				}
			}
		}).result;
	}

	private static <T extends TokenCallback> T parse(String input, T callback) {
		var m = MUL_PATTERN.matcher(input);
		while (m.find()) {
			switch (m.group()) {
				case "do()" -> callback.onDo();
				case "don't()" -> callback.onDont();
				default ->
						callback.onMul(Long.parseLong(m.group(1)), Long.parseLong((m.group(2))));
			}
		}
		return callback;
	}
}
