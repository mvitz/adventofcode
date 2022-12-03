package de.mvitz.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

record BatchCollector<T>(int batchSize)
        implements Collector<T, List<List<T>>, List<List<T>>> {

    @Override
    public Supplier<List<List<T>>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<List<T>>, T> accumulator() {
        return (result, item) -> {
            if (result.isEmpty()) {
                result.add(new ArrayList<>());
            }

            var currentBatch = result.get(result.size() - 1);
            if (currentBatch.size() >= batchSize) {
                currentBatch = new ArrayList<>();
                result.add(currentBatch);
            }

            currentBatch.add(item);
        };
    }

    @Override
    public BinaryOperator<List<List<T>>> combiner() {
        return (first, second) -> {
            first.addAll(second);
            return first;
        };
    }

    @Override
    public Function<List<List<T>>, List<List<T>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(IDENTITY_FINISH);
    }

    public static <T> Collector<T, List<List<T>>, List<List<T>>> batchesOfSize(int batchSize) {
        return new BatchCollector<>(batchSize);
    }
}
