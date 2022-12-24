package de.mvitz.aoc2022;

import java.util.*;
import java.util.stream.Collectors;

final class Day13 {

    private Day13() {
    }

    public static long sumOfIndicesOfRightOrderedPacketPairsFrom(String input) {
        var sum = 0L;

        final var pairs = input.split("\n\n");

        for (int i = 0; i < pairs.length; i++) {
            final var pair = pairs[i].split("\n");

            final var left = Packet.parse(pair[0]);
            final var right = Packet.parse(pair[1]);

            if (left.compareTo(right) < 0) {
                sum += (i + 1);
            }
        }

        return sum;
    }

    record Packet(PacketData.List data) implements Comparable<Packet> {

        @Override
        public int compareTo(Packet other) {
            return this.data.compareTo(other.data);
        }

        public static Packet of(PacketData... data) {
            return new Packet(PacketData.List.of(data));
        }

        public static Packet parse(String input) {
            return PacketParser.parse(input);
        }
    }

    sealed interface PacketData extends Comparable<PacketData> {

        record Value(int value) implements PacketData {

            @Override
            public int compareTo(PacketData other) {
                return switch (other) {
                    case Value v -> this.value - v.value;
                    case List l -> asList().compareTo(l);
                };
            }

            public PacketData asList() {
                return List.of(this);
            }

            @Override
            public String toString() {
                return Integer.toString(value);
            }

            public static Value of(int value) {
                return new Value(value);
            }
        }

        record List(java.util.List<PacketData> values) implements PacketData {

            @Override
            public int compareTo(PacketData other) {
                if (other instanceof Value v) {
                    return this.compareTo(v.asList());
                }

                if (!(other instanceof List l)) {
                    throw new IllegalStateException("Unknown packet data: " + other);
                }

                for (int i = 0; i < values.size(); i++) {
                    if (l.values.size() <= i) {
                        return 1;
                    }

                    final var left = values.get(i);
                    final var right = l.values.get(i);

                    final var comparison = left.compareTo(right);
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                if (l.values.size() > this.values().size()) {
                    return -1;
                }
                return 0;
            }

            public void append(PacketData data) {
                values.add(data);
            }

            @Override
            public String toString() {
                return values.stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining(",", "[", "]"));
            }

            public static List empty() {
                return of();
            }

            public static List of(PacketData... values) {
                return new List(new ArrayList<>(Arrays.asList(values)));
            }
        }
    }

    private static final class PacketParser {

        private PacketParser() {
        }

        private static PacketData.Value parseValue(Deque<Character> input) {
            final var value = new StringBuilder();
            while (!input.isEmpty() && Character.isDigit(input.peekFirst())) {
                value.append(input.pollFirst());
            }
            return PacketData.Value.of(Integer.parseInt(value.toString()));
        }

        private static void parse(PacketData.List list, Deque<Character> input) {
            if (input.isEmpty()) {
                return;
            }

            final var nextChar = input.peekFirst();

            if (nextChar == ']') {
                input.removeFirst();
                return;
            }

            if (nextChar == ',') {
                input.removeFirst();
            } else if (Character.isDigit(nextChar)) {
                list.append(parseValue(input));
            } else { // nextChar == '['
                input.removeFirst();
                final var newList = PacketData.List.empty();
                list.append(newList);
                parse(newList, input);
            }

            // continue
            parse(list, input);
        }

        public static Packet parse(String input) {
            final var chars = new ArrayDeque<>(
                    input.substring(1, input.length() - 1).chars().mapToObj(c -> (char) c).toList());

            final var data = PacketData.List.empty();
            parse(data, chars);
            return new Packet(data);
        }
    }
}
