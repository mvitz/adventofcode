package de.mvitz.aoc2024.utils;

import java.util.function.Function;

public record Pair<L, R>(L left, R right) {

	public <T> Pair<T, R> mapLeft(Function<L, T> function) {
		return new Pair<>(function.apply(left), right);
	}

	public <T> Pair<L, T> mapRight(Function<R, T> function) {
		return new Pair<>(left, function.apply(right));
	}

	public static <L, R> Pair<L, R> of(L left, R right) {
		return new Pair<>(left, right);
	}
}
