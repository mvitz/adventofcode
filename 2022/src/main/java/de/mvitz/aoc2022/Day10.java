package de.mvitz.aoc2022;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

final class Day10 {

    private Day10() {
    }

    public static int findSumOfRequiredSignalStrengthsFrom(String input) {
        final var cpu = new Cpu();
        parse(input).forEach(cpu::queue);

        int sumOfSignalStrengths = 0;
        for (var cycles : List.of(19, 40, 40, 40, 40, 40)) {
            cpu.run(cycles);
            sumOfSignalStrengths += (cpu.runCycles + 1) * cpu.registerX;

        }
        return sumOfSignalStrengths;
    }

    private static Stream<Instruction> parse(String input) {
        return input
                .lines()
                .map(Day10::toInstruction);
    }

    private static Instruction toInstruction(String line) {
        if ("noop".equals(line)) {
            return NoOpInstruction.INSTANCE;
        }
        if (line.startsWith("addx ")) {
            final var value = Integer.parseInt(line.substring(5));
            return new AddxInstruction(value);
        }
        throw new IllegalStateException("Unknown instruction: " + line);
    }

    private sealed interface Instruction {

        boolean onCycle(Cpu cpu);
    }

    private static final class NoOpInstruction implements Instruction {

        public static final NoOpInstruction INSTANCE = new NoOpInstruction();

        @Override
        public boolean onCycle(Cpu cpu) {
            return true;
        }
    }

    private static final class AddxInstruction implements Instruction {

        private int cyclesLeft = 2;
        private final int value;

        public AddxInstruction(int value) {
            this.value = value;
        }

        @Override
        public boolean onCycle(Cpu cpu) {
            if (--cyclesLeft > 0) {
                return false;
            }

            cpu.registerX += value;
            return true;
        }
    }

    private static final class Cpu {

        private int registerX = 1;
        private int runCycles = 0;

        private final Deque<Instruction> instructions = new ArrayDeque<>();

        public void queue(Instruction instruction) {
            this.instructions.addLast(instruction);
        }

        public void run(int cycles) {
            for (int i = 0; i < cycles; i++) {
                runCycles++;
                final var instruction = instructions.peekFirst();
                final var finished = instruction.onCycle(this);
                if (finished) {
                    instructions.removeFirst();
                }
            }
        }
    }
}
