package de.mvitz.aoc2024;

import de.mvitz.aoc2024.utils.Files;

import java.io.IO;

import static de.mvitz.aoc2024.day05.Day05.sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates;
import static de.mvitz.aoc2024.day05.Day05.sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering;

public class Main {

	@SuppressWarnings("preview")
	public static void main() {
		var updatePlan = Files.contentOf("day05/input.txt");

		var result = sumOfMiddlePageNumbersFromCorrectlyOrderedUpdates(updatePlan);
		IO.println("Sum of middlepage numbers for correctly ordered pages is: " + result);

		result = sumOfMiddlePageNumbersFromIncorrectlyOrderedUpdatesAfterOrdering(updatePlan);
		IO.println("Sum of middlepage numbers for incorrectly ordered pages is: " + result);
	}
}
