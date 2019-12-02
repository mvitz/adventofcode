package de.mvitz.aoc2019;

/*
--- Day 1: The Tyranny of the Rocket Equation ---

The Elves quickly load you into a spacecraft and prepare to launch.

At the first Go / No Go poll, every Elf is Go until the Fuel Counter-Upper. They haven't determined the amount of fuel
required yet.

Fuel required to launch a given module is based on its mass. Specifically, to find the fuel required for a module, take
its mass, divide by three, round down, and subtract 2.

For example:

 - For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
 - For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
 - For a mass of 1969, the fuel required is 654.
 - For a mass of 100756, the fuel required is 33583.

The Fuel Counter-Upper needs to know the total fuel requirement. To find it, individually calculate the fuel needed for
the mass of each module (your puzzle input), then add together all the fuel values.

What is the sum of the fuel requirements for all of the modules on your spacecraft?

--- Part Two ---

During the second Go / No Go poll, the Elf in charge of the Rocket Equation Double-Checker stops the launch sequence.
Apparently, you forgot to include additional fuel for the fuel you just added.

Fuel itself requires fuel just like a module - take its mass, divide by three, round down, and subtract 2. However, that
fuel also requires fuel, and that fuel requires fuel, and so on. Any mass that would require negative fuel should
instead be treated as if it requires zero fuel; the remaining mass, if any, is instead handled by wishing really hard,
which has no mass and is outside the scope of this calculation.

So, for each module mass, calculate its fuel and add it to the total. Then, treat the fuel amount you just calculated as
the input mass and repeat the process, continuing until a fuel requirement is zero or negative. For example:

 - A module of mass 14 requires 2 fuel. This fuel requires no further fuel (2 divided by 3 and rounded down is 0, which
   would call for a negative fuel), so the total fuel required is still just 2.
 - At first, a module of mass 1969 requires 654 fuel. Then, this fuel requires 216 more fuel (654 / 3 - 2). 216 then
   requires 70 more fuel, which requires 21 fuel, which requires 5 fuel, which requires no further fuel. So, the total
   fuel required for a module of mass 1969 is 654 + 216 + 70 + 21 + 5 = 966.
 - The fuel required by a module of mass 100756 and its fuel is:
   33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.

What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account the
mass of the added fuel? (Calculate the fuel requirements for each module separately, then add them all up at the end.)
 */
public class Day01 {

    public static void main(String[] args) {
        // Part 1
        long fuelRequirement = MODULES.lines()
                .mapToLong(Long::parseLong)
                .map(Day01::fuelRequiredFor)
                .sum();
        System.out.println("Part 1: " + fuelRequirement);

        // Part 2
        long totalFuelRequirement = MODULES.lines()
                .mapToLong(Long::parseLong)
                .map(Day01::totalFuelRequiredFor)
                .sum();
        System.out.println("Part 2: " + totalFuelRequirement);
    }

    private static long fuelRequiredFor(long mass) {
        return (mass / 3) - 2;
    }

    private static long totalFuelRequiredFor(long mass) {
        long totalFuelRequired = 0;
        long fuelRequired = fuelRequiredFor(mass);
        while (fuelRequired > 0) {
            totalFuelRequired += fuelRequired;
            fuelRequired = fuelRequiredFor(fuelRequired);
        }
        return totalFuelRequired;
    }

    private static final String MODULES = """
119965
69635
134375
71834
124313
109114
80935
146441
120287
85102
148451
69703
143836
75280
83963
108849
133032
109359
78119
104402
89156
116946
132008
131627
124358
56060
141515
75639
146945
95026
99256
57751
148607
100505
65002
78485
84473
112331
82177
111298
131964
125753
63970
77100
90922
119326
51747
104086
141344
54409
69642
70193
109730
73782
92049
90532
147093
62719
79829
142640
85242
128001
71403
75365
90146
147194
76903
68895
56817
142352
77843
64082
106953
115590
87224
58146
134018
127111
51996
134433
148768
103906
52848
108577
77646
95930
67333
98697
55870
78927
148519
68724
93076
73736
140291
121184
111768
71920
104822
87534
            """;
}
