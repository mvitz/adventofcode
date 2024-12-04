package de.mvitz.aoc2024.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Gatherer;

@SuppressWarnings("preview")
public final class Gatherers {

	private Gatherers() {
	}

	@SuppressWarnings("java:S1452")
	public static <T, R> Gatherer<T, ?, R> mapWithIndex(
			BiFunction<Integer, ? super T, ? extends R> mapper) {
		return Gatherer.ofSequential(
				AtomicInteger::new,
				(state, element, downstream) ->
						downstream.push(mapper.apply(state.getAndIncrement(), element)));
	}
}
