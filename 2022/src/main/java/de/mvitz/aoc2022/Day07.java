package de.mvitz.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

final class Day07 {

    private Day07() {
    }

    public static int findSumOfTotalSizeOfDirectoriesBelow100000(String input) {
        final var terminal = new Terminal();
        parse(input,
                terminal::cd,
                terminal::mkdir,
                terminal::mkfile);
        terminal.cd("/");

        var sum = new AtomicInteger(0);
        terminal.pwd.visitDirectories(dir -> {
            final var size = dir.size();
            if (size < 100_000) {
                sum.getAndAdd(size);
            }
        });

        return sum.get();
    }

    public static void parse(String input,
                             Consumer<String> onCd,
                             Consumer<String> onLsDir,
                             ObjIntConsumer<String> onLsFile) {
        input.lines().forEach(line -> {
            if (line.startsWith("$ cd")) {
                final var target = line.substring(5);
                onCd.accept(target);
            } else if (line.startsWith("$ ls")) {
            } else if (line.startsWith("dir ")) {
                final var dirName = line.substring(4);
                onLsDir.accept(dirName);
            } else {
                final var sizeWithFileName = line.split(" ");
                onLsFile.accept(sizeWithFileName[1], Integer.parseInt(sizeWithFileName[0]));
            }
        });
    }


    private static final class Terminal {

        private Directory pwd = Directory.newRoot();

        public void cd(String target) {
            if ("/".equals(target)) {
                while (!pwd.isRoot()) {
                    pwd = pwd.parent();
                }
                return;
            }

            if ("..".equals(target)) {
                if (!pwd.isRoot()) {
                    pwd = pwd.parent();
                }
                return;
            }

            pwd = pwd.cd(target);
        }

        public void mkdir(String target) {
            pwd.mkdir(target);
        }

        public void mkfile(String name, int size) {
            pwd.files.add(new File(name, size));
        }
    }

    private record File(String name, int size) {
    }

    private record Directory(String name, Directory parent,
                             List<File> files,
                             List<Directory> subDirectories) {

        public void visitDirectories(Consumer<Directory> onDir) {
            subDirectories.forEach(subDir -> subDir.visitDirectories(onDir));
            onDir.accept(this);
        }

        public int size() {
            return subDirectories.stream().mapToInt(Directory::size).sum()
                    + files.stream().mapToInt(File::size).sum();
        }

        public boolean isRoot() {
            return parent == null;
        }

        public void mkdir(String name) {
            if (subDirectories.stream()
                    .map(Directory::name)
                    .anyMatch(name::equals)) {
                return;
            }
            final var dir = new Directory(name, this,
                    new ArrayList<>(), new ArrayList<>());
            subDirectories.add(dir);
        }

        public Directory cd(String name) {
            return subDirectories.stream()
                    .filter(dir -> name.equals(dir.name))
                    .findFirst()
                    .orElseThrow();
        }

        public static Directory newRoot() {
            return new Directory("", null,
                    new ArrayList<>(), new ArrayList<>());
        }
    }
}
