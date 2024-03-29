package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day10.crtAfter240CyclesFor;
import static de.mvitz.aoc2022.Day10.findSumOfRequiredSignalStrengthsFrom;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day10Tests {

    @Test
    void part1_example() {
        var input = contentOf("day10_example.txt");

        var sumOfRequiredSignalStrengths = findSumOfRequiredSignalStrengthsFrom(input);

        assertThat(sumOfRequiredSignalStrengths, is(13_140));
    }

    @Test
    void part1() {
        var input = contentOf("day10.txt");

        var sumOfRequiredSignalStrengths = findSumOfRequiredSignalStrengthsFrom(input);

        assertThat(sumOfRequiredSignalStrengths, is(14_760));
    }

    @Test
    void part2_example() {
        var input = contentOf("day10_example.txt");

        var crtAfter240Cycles = crtAfter240CyclesFor(input);

        assertThat(crtAfter240Cycles, is("""
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
                """));
    }

    @Test
    void part2() {
        var input = contentOf("day10.txt");

        var crtAfter240Cycles = crtAfter240CyclesFor(input);

        assertThat(crtAfter240Cycles, is("""
                ####.####..##..####.###..#..#.###..####.
                #....#....#..#.#....#..#.#..#.#..#.#....
                ###..###..#....###..#..#.#..#.#..#.###..
                #....#....#.##.#....###..#..#.###..#....
                #....#....#..#.#....#.#..#..#.#.#..#....
                ####.#.....###.####.#..#..##..#..#.####.
                """));
    }
}
