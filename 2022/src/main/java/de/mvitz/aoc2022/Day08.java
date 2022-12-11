package de.mvitz.aoc2022;

import java.util.stream.Stream;

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

        public boolean isBetween(Tree first, Tree second) {
            if (first.row == second.row) {
                return col > Math.min(first.col, second.col)
                        && col < Math.max(first.col, second.col);
            }
            if (first.col == second.col) {
                return row > Math.min(first.row, second.row)
                        && row < Math.max(first.row, second.row);
            }
            return false;
        }

        public boolean isHigherThanAll(Stream<Tree> otherTrees) {
            return otherTrees
                    .mapToInt(Tree::height)
                    .max()
                    .orElse(Integer.MAX_VALUE) > height;
        }

        public boolean isLowerThanAll(Stream<Tree> otherTrees) {
            return otherTrees
                    .mapToInt(Tree::height)
                    .max()
                    .orElse(-1) < height;
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
            return tree.isLowerThanAll(leftTreesOf(tree))
                    || tree.isLowerThanAll(rightTreesOf(tree))
                    || tree.isLowerThanAll(topTreesOf(tree))
                    || tree.isLowerThanAll(bottomTreesOf(tree));
        }

        public long scenicScoreOf(Tree tree) {
            return leftTreesOf(tree)
                    .filter(candidate -> candidate.isHigherThanAll(leftTreesOf(tree).filter(left -> left.isBetween(candidate, tree))))
                    .count()
                    * rightTreesOf(tree)
                    .filter(candidate -> candidate.isHigherThanAll(rightTreesOf(tree).filter(right -> right.isBetween(candidate, tree))))
                    .count()
                    * topTreesOf(tree)
                    .filter(candidate -> candidate.isHigherThanAll(topTreesOf(tree).filter(top -> top.isBetween(candidate, tree))))
                    .count()
                    * bottomTreesOf(tree)
                    .filter(candidate -> candidate.isHigherThanAll(bottomTreesOf(tree).filter(bottom -> bottom.isBetween(candidate, tree))))
                    .count();
        }

        public Tree treeAt(int row, int col) {
            return trees[row][col];
        }

        private Stream<Tree> leftTreesOf(Tree tree) {
            return trees()
                    .filter(candidate -> candidate.row == tree.row && candidate.col < tree.col);
        }

        private Stream<Tree> rightTreesOf(Tree tree) {
            return trees()
                    .filter(candidate -> candidate.row == tree.row && candidate.col > tree.col);
        }

        private Stream<Tree> topTreesOf(Tree tree) {
            return trees()
                    .filter(candidate -> candidate.col == tree.col && candidate.row < tree.row);
        }

        private Stream<Tree> bottomTreesOf(Tree tree) {
            return trees()
                    .filter(candidate -> candidate.col == tree.col && candidate.row > tree.row);
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
