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
                        // find right most block
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

        @SuppressWarnings("java:S5413")
        public Disk compact() {
            for (var fileId = highestFileId(); fileId > -1; fileId--) {
                var file = new File(fileId);
                var fileIndex = blocks.indexOf(file);
                var fileLength = blocks.lastIndexOf(file) - fileIndex + 1;

                var newIndex = freeSpaceOf(fileLength);
                if (newIndex > -1 && newIndex < blocks.indexOf(file)) {
                    for (var i = 0; i < fileLength; i++) {
                        // move block of file into new position
                        blocks.remove(fileIndex + i);
                        blocks.add(newIndex + i, file);

                        // move free space into previous block position
                        blocks.remove(newIndex + i + 1);
                        blocks.add(fileIndex + i, FREE_SPACE);
                    }
                }
            }

            return this;
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
