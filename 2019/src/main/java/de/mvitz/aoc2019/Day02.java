package de.mvitz.aoc2019;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/*
--- Day 2: 1202 Program Alarm ---

On the way to your gravity assist around the Moon, your ship computer beeps angrily about a "1202 program alarm". On the
radio, an Elf is already explaining how to handle the situation: "Don't worry, that's perfectly norma--" The ship
computer bursts into flames.

You notify the Elves that the computer's magic smoke seems to have escaped. "That computer ran Intcode programs like the
gravity assist program it was working on; surely there are enough spare parts up there to build a new Intcode computer!"

An Intcode program is a list of integers separated by commas (like 1,0,0,3,99). To run one, start by looking at the
first integer (called position 0). Here, you will find an opcode - either 1, 2, or 99. The opcode indicates what to do;
for example, 99 means that the program is finished and should immediately halt. Encountering an unknown opcode means
something went wrong.

Opcode 1 adds together numbers read from two positions and stores the result in a third position. The three integers
immediately after the opcode tell you these three positions - the first two indicate the positions from which you should
read the input values, and the third indicates the position at which the output should be stored.

For example, if your Intcode computer encounters 1,10,20,30, it should read the values at positions 10 and 20, add those
values, and then overwrite the value at position 30 with their sum.

Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them. Again, the three
integers after the opcode indicate where the inputs and outputs are, not their values.

Once you're done processing an opcode, move to the next one by stepping forward 4 positions.

For example, suppose you have the following program:

1,9,10,3,2,3,11,0,99,30,40,50

For the purposes of illustration, here is the same program split into multiple lines:

1,9,10,3,
2,3,11,0,
99,
30,40,50

The first four integers, 1,9,10,3, are at positions 0, 1, 2, and 3. Together, they represent the first opcode (1,
addition), the positions of the two inputs (9 and 10), and the position of the output (3). To handle this opcode, you
first need to get the values at the input positions: position 9 contains 30, and position 10 contains 40. Add these
numbers together to get 70. Then, store this value at the output position; here, the output position (3) is at position
3, so it overwrites itself. Afterward, the program looks like this:

1,9,10,70,
2,3,11,0,
99,
30,40,50

Step forward 4 positions to reach the next opcode, 2. This opcode works just like the previous, but it multiplies
instead of adding. The inputs are at positions 3 and 11; these positions contain 70 and 50 respectively. Multiplying
these produces 3500; this is stored at position 0:

3500,9,10,70,
2,3,11,0,
99,
30,40,50

Stepping forward 4 more positions arrives at opcode 99, halting the program.

Here are the initial and final states of a few more small programs:

 - 1,0,0,0,99 becomes 2,0,0,0,99 (1 + 1 = 2).
 - 2,3,0,3,99 becomes 2,3,0,6,99 (3 * 2 = 6).
 - 2,4,4,5,99,0 becomes 2,4,4,5,99,9801 (99 * 99 = 9801).
 - 1,1,1,4,99,5,6,0,99 becomes 30,1,1,4,2,5,6,0,99.

Once you have a working computer, the first step is to restore the gravity assist program (your puzzle input) to the
"1202 program alarm" state it had just before the last computer caught fire. To do this, before running the program,
replace position 1 with the value 12 and replace position 2 with the value 2. What value is left at position 0 after the
program halts?
 */
public class Day02 {

    public static void main(String[] args) {
        // Part 1
        Intcode code = Intcode.load(GRAVITY_ASSIST_PROGRAM);
        code.set(1, 12);
        code.set(2, 2);

        code.execute();

        System.out.println(code);
    }

    private static final class Intcode {

        private final List<Integer> program;

        public Intcode(List<Integer> program) {
            this.program = program;
        }

        public int get(int position) {
            return program.get(position);
        }

        public void set(int position, int value) {
            program.set(position, value);
        }

        public void execute() {
            int currentPosition = 0;
            while(currentPosition >= 0) {
                int opcode = get(currentPosition);
                currentPosition = switch (opcode) {
                    case 1 -> handleAddition(currentPosition);
                    case 2 -> handleMultiplication(currentPosition);
                    case 99 -> handleHalt(currentPosition);
                    default -> handleUnknown(currentPosition);
                };
            }
        }

        private int handleAddition(int position) {
            set(get(position + 3), read(position + 1) + read(position + 2));
            return position + 4;
        }

        private int handleMultiplication(int position) {
            set(get(position + 3), read(position + 1) * read(position + 2));
            return position + 4;
        }

        private int handleHalt(int position) {
            return -1;
        }

        private int handleUnknown(int position) {
            return -42;
        }

        private int read(int position) {
            int pointer = get(position);
            return get(pointer);
        }

        @Override
        public String toString() {
            return program.toString();
        }

        public static Intcode load(String code) {
            List<Integer> program = asList(code.split(",")).stream()
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(toList());
            return new Intcode(program);
        }
    }

    private static final String GRAVITY_ASSIST_PROGRAM =
            "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,19,5,23,2,13,23,27,1,10,27,31,2,6,31,35,1,9,35,39,2,10,39,43,1,43,9,47,1,47,9,51,2,10,51,55,1,55,9,59,1,59,5,63,1,63,6,67,2,6,67,71,2,10,71,75,1,75,5,79,1,9,79,83,2,83,10,87,1,87,6,91,1,13,91,95,2,10,95,99,1,99,6,103,2,13,103,107,1,107,2,111,1,111,9,0,99,2,14,0,0";
}
