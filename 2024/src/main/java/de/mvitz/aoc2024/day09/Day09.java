package de.mvitz.aoc2024.day09;

import de.mvitz.aoc2024.day09.Day09.Block.File;
import de.mvitz.aoc2024.day09.Day09.Block.FreeSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static de.mvitz.aoc2024.day09.Day09.Block.FreeSpace.FREE_SPACE;
import static de.mvitz.aoc2024.utils.Gatherers.mapWithIndex;

public final class Day09 {

    private Day09() {
    }

    public static long filesystemChecksumAfterFragmentationOf(String input) {
        return Disk.from(input)
                .fragment()
                .checksum();
    }

    private static final class Disk {

        private List<Block> blocks;

        private Disk(List<Block> blocks) {
            this.blocks = blocks;
        }

        public Disk fragment() {
            var fragmentedBlocks = new ArrayList<Block>();

            var j = blocks.size() - 1;
            for (var i = 0; i <= j; i++) {
                var block = blocks.get(i);
                switch (block) {
                    case File _ -> fragmentedBlocks.add(block);
                    case FreeSpace _ -> {
                        while (!(blocks.get(j) instanceof File)) {
                            j--;
                        }
                        if (j > i) {
                            fragmentedBlocks.add(blocks.get(j));
                            j--;
                        }
                    }
                }
            }

            fragmentedBlocks.addAll(
                    Stream.generate(() -> FREE_SPACE)
                            .limit((long) blocks.size() - fragmentedBlocks.size())
                            .toList());

            blocks = fragmentedBlocks;

            return this;
        }

        @SuppressWarnings("preview")
        public long checksum() {
            return blocks.stream()
                    .gather(mapWithIndex((i, block) -> block.checksum(i)))
                    .mapToLong(checksum -> checksum)
                    .sum();
        }

        public static Disk from(String diskMap) {
            return new Disk(blocksFrom(diskMap));
        }

        private static List<Block> blocksFrom(String diskMap) {
            var isFreeSpace = new AtomicBoolean(false);
            var id = new AtomicInteger(0);

            return Arrays.stream(diskMap.split(""))
                    .map(Long::parseLong)
                    .flatMap(blockLength -> {
                        Block block;
                        if (isFreeSpace.get()) {
                            isFreeSpace.set(false);
                            block = FREE_SPACE;
                        } else {
                            isFreeSpace.set(true);
                            block = new File(id.getAndIncrement());
                        }
                        return Stream.generate(() -> block).limit(blockLength);
                    })
                    .toList();
        }
    }

    sealed interface Block {

        long checksum(int position);

        record File(long id) implements Block {

            @Override
            public long checksum(int position) {
                return id * position;
            }
        }

        @SuppressWarnings("java:S6548")
        enum FreeSpace implements Block {
            FREE_SPACE;

            @Override
            public long checksum(int position) {
                return 0;
            }
        }
    }
}
