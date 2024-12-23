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

    public static long filesystemChecksumAfterCompactionOf(String input) {
        return Disk.from(input)
                .compact()
                .checksum();
    }

    private record Disk(List<Block> blocks) {

        public Disk fragment() {
                var j = blocks.size() - 1;
                for (var i = 0; i <= j; i++) {
                    if (blocks.get(i) instanceof FreeSpace) {
                        // find right most block
                        while (!(blocks.get(j) instanceof File)) {
                            j--;
                        }
                        if (j > i) {
                            move(j, i);
                            j--;
                        }
                    }
                }

                return this;
            }

            public Disk compact() {
                for (var fileId = highestFileId(); fileId > -1; fileId--) {
                    var file = new File(fileId);
                    var fileIndex = blocks.indexOf(file);
                    var fileLength = blocks.lastIndexOf(file) - fileIndex + 1;

                    var newIndex = freeSpaceOf(fileLength);
                    if (newIndex > -1 && newIndex < blocks.indexOf(file)) {
                        for (var i = 0; i < fileLength; i++) {
                            move(fileIndex + i, newIndex + i);
                        }
                    }
                }

                return this;
            }

            private void move(int from, int to) {
                if (from <= to) {
                    throw new IllegalArgumentException("Moving only works to the left!");
                }
                // move from into to position
                var block = blocks.remove(from);
                blocks.add(to, block);

                // move to into from position
                block = blocks.remove(to + 1);
                blocks.add(from, block);
            }

            @SuppressWarnings("preview")
            public long checksum() {
                return blocks.stream()
                        .gather(mapWithIndex((i, block) -> block.checksum(i)))
                        .mapToLong(checksum -> checksum)
                        .sum();
            }

            private long highestFileId() {
                return blocks.stream()
                        .filter(File.class::isInstance)
                        .map(File.class::cast)
                        .mapToLong(File::id)
                        .max()
                        .orElseThrow();
            }

            private int freeSpaceOf(int length) {
                for (var i = 0; i + length < blocks.size(); i++) {
                    if (blocks.get(i) == FREE_SPACE && blocks.subList(i, i + length).stream().allMatch(FREE_SPACE::equals)) {
                        return i;
                    }
                }
                return -1;
            }

            public static Disk from(String diskMap) {
                return new Disk(blocksFrom(diskMap));
            }

            private static List<Block> blocksFrom(String diskMap) {
                var isFreeSpace = new AtomicBoolean(false);
                var id = new AtomicInteger(0);

                return new ArrayList<>(Arrays.stream(diskMap.split(""))
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
                        .toList());
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
