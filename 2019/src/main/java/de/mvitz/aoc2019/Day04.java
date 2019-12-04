package de.mvitz.aoc2019;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/*
--- Day 4: Secure Container ---

You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the password on
a sticky note, but someone threw it out.

However, they do remember a few key facts about the password:

 - It is a six-digit number.
 - The value is within the range given in your puzzle input.
 - Two adjacent digits are the same (like 22 in 122345).
 - Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

Other than the range rule, the following are true:

 - 111111 meets these criteria (double 11, never decreases).
 - 223450 does not meet these criteria (decreasing pair of digits 50).
 - 123789 does not meet these criteria (no double).

How many different passwords within the range given in your puzzle input meet these criteria?

Your puzzle input is 264793-803935.
 */
public class Day04 {

    public static void main(String[] args) {
        // Part 1
        List<List<Integer>> matchingPasswords = range(264793, 803935)
                .mapToObj(Day04::toDigits)
                .filter(Day04::containsTwoSameAdjacentDigits)
                .filter(Day04::digitsNeverDecrease)
                .collect(toList());
        System.out.println("Part 1: " + matchingPasswords.size());
    }

    private static List<Integer> toDigits(int number) {
        return Integer.toString(number)
                .chars()
                .mapToObj(Character::getNumericValue)
                .collect(toList());
    }

    private static boolean containsTwoSameAdjacentDigits(List<Integer> digits) {
        for(int i = 0; i < digits.size() - 1; i++) {
            if (digits.get(i) == digits.get(i + 1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean digitsNeverDecrease(List<Integer> digits) {
        for(int i = 0; i < digits.size() - 1; i++) {
            if (digits.get(i) > digits.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
