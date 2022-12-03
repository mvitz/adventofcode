package de.mvitz.aoc2022;

import org.junit.jupiter.api.Test;

import static de.mvitz.aoc2022.Day03.sumOfBadgeItemTypesFor;
import static de.mvitz.aoc2022.Day03.sumOfWrongPackedItemTypePrioritiesFor;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day03Tests {

    @Test
    void part1_example() {
        var input = contentOf("day03_example.txt");

        var sumOfWrongPackedItemTypePriorities = sumOfWrongPackedItemTypePrioritiesFor(input);

        assertThat(sumOfWrongPackedItemTypePriorities, is(157));
    }

    @Test
    void part1() {
        var input = contentOf("day03.txt");

        var sumOfWrongPackedItemTypePriorities = sumOfWrongPackedItemTypePrioritiesFor(input);

        assertThat(sumOfWrongPackedItemTypePriorities, is(7_990));
    }

    @Test
    void part2_example() {
        var input = contentOf("day03_example.txt");

        var sumOfBadgeItemTypes = sumOfBadgeItemTypesFor(input);

        assertThat(sumOfBadgeItemTypes, is(70));
    }

    @Test
    void part2() {
        var input = contentOf("day03.txt");

        var sumOfBadgeItemTypes = sumOfBadgeItemTypesFor(input);

        assertThat(sumOfBadgeItemTypes, is(2_602));
    }
}
