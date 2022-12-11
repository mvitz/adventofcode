package de.mvitz.aoc2022;

import java.util.stream.Stream;

import static java.util.Comparator.comparing;

final class Day08 {

    private Day08() {
    }

    public static long findNumberOfVisibleTreesFor(String input) {
        final var grid = Grid.from(input);
        return grid.trees()
                .filter(grid::isTreeVisible)
                .count();
    }

    public static long findHighestScenicScoreFor(String input) {
        final var grid = Grid.from(input);
        return grid.trees()
                .mapToLong(grid::scenicScoreOf)
                .max()
                .orElse(-1);
    }

    private record Tree(int row, int col, int height) {

        public boolean isHigherOrEqualThan(Tree other) {
            return height >= other.height;
        }

        public boolean isHigherThan(Tree other) {
            return height > other.height;
        }

        public boolean isHigherThanAll(Stream<Tree> otherTrees) {
            return otherTrees.allMatch(this::isHigherThan);
        }
    }

    private static final class Grid {

        private final Tree[][] trees;

        private Grid(Tree[][] trees) {
            this.trees = trees;
        }

        public Stream<Tree> trees() {
            final var builder = Stream.<Tree>builder();
            for (var row = 0; row < height(); row++) {
                for (var col = 0; col < width(); col++) {
                    builder.add(treeAt(row, col));
                }
            }
            return builder.build();
        }

        public boolean isTreeVisible(Tree tree) {
            return tree.isHigherThanAll(leftTreesOf(tree))
                    || tree.isHigherThanAll(rightTreesOf(tree))
                    || tree.isHigherThanAll(topTreesOf(tree))
                    || tree.isHigherThanAll(bottomTreesOf(tree));
        }

        public long scenicScoreOf(Tree tree) {
            final long left = leftTreesOf(tree)
                    .filter(candidate -> candidate.isHigherOrEqualThan(tree))
                    .max(comparing(Tree::col))
                    .map(other -> tree.col - other.col)
                    .orElse(tree.col);
            final var right = rightTreesOf(tree)
                    .filter(candidate -> candidate.isHigherOrEqualThan(tree))
                    .min(comparing(Tree::col))
                    .map(other -> other.col - tree.col)
                    .orElse(width() - 1 - tree.col);
            final var top = topTreesOf(tree)
                    .filter(candidate -> candidate.isHigherOrEqualThan(tree))
                    .max(comparing(Tree::row))
                    .map(other -> tree.row - other.row)
                    .orElse(tree.row);
            final var bottom = bottomTreesOf(tree)
                    .filter(candidate -> candidate.isHigherOrEqualThan(tree))
                    .min(comparing(Tree::row))
                    .map(other -> other.row - tree.row)
                    .orElse(height() - 1 - tree.row);
            return left * right * top * bottom;
        }

        private Tree treeAt(int row, int col) {
            return trees[row][col];
        }

        private Stream<Tree> leftTreesOf(Tree tree) {
            final var builder = Stream.<Tree>builder();
            for (var col = 0; col < tree.col; col++) {
                builder.add(treeAt(tree.row, col));
            }
            return builder.build();
        }

        private Stream<Tree> rightTreesOf(Tree tree) {
            final var builder = Stream.<Tree>builder();
            for (var col = tree.col + 1; col < width(); col++) {
                builder.add(treeAt(tree.row, col));
            }
            return builder.build();
        }

        private Stream<Tree> topTreesOf(Tree tree) {
            final var builder = Stream.<Tree>builder();
            for (var row = 0; row < tree.row; row++) {
                builder.add(treeAt(row, tree.col));
            }
            return builder.build();
        }

        private Stream<Tree> bottomTreesOf(Tree tree) {
            final var builder = Stream.<Tree>builder();
            for (var row = tree.row + 1; row < height(); row++) {
                builder.add(treeAt(row, tree.col));
            }
            return builder.build();
        }

        public int height() {
            return trees.length;
        }

        public int width() {
            return trees[0].length;
        }

        public static Grid from(String input) {
            final var lines = input.lines().toList();
            final var grid = new Tree[lines.size()][];

            for (var rowIdx = 0; rowIdx < lines.size(); rowIdx++) {
                final var line = lines.get(rowIdx);

                final var row = new Tree[line.length()];
                grid[rowIdx] = row;

                final var trees = line.split("");
                for (var colIdx = 0; colIdx < trees.length; colIdx++) {
                    final var height = Integer.parseInt(trees[colIdx]);
                    row[colIdx] = new Tree(rowIdx, colIdx, height);
                }
            }

            return new Grid(grid);
        }
    }
}
