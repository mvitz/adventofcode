package de.mvitz.aoc2022;

import de.mvitz.aoc2022.Day13.Packet;
import de.mvitz.aoc2022.Day13.PacketData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static de.mvitz.aoc2022.Day13.sumOfIndicesOfRightOrderedPacketPairsFrom;
import static de.mvitz.aoc2022.Utils.contentOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class Day13Tests {

    @Test
    void part1_example() {
        var input = contentOf("day13_example.txt");

        var sumOfIndicesOfRightOrderedPacketPairs = sumOfIndicesOfRightOrderedPacketPairsFrom(input);

        assertThat(sumOfIndicesOfRightOrderedPacketPairs, is(13L));
    }

    @ParameterizedTest
    @MethodSource("parsingExamples")
    void parse(String input, Packet expectedPacket) {
        var packet = Packet.parse(input);

        assertThat(packet, is(equalTo(expectedPacket)));
    }

    @ParameterizedTest
    @MethodSource("compareToExamples")
    void compareTo(Packet left, Packet right, boolean leftShouldBeBeforeRight) {
        var comparison = left.compareTo(right);

        assertThat(comparison < 0, is(leftShouldBeBeforeRight));
    }

    static Stream<Arguments> parsingExamples() {
        return Stream.of(
                Arguments.of("[1,1,3,1,1]",
                        Packet.of(
                                PacketData.Value.of(1),
                                PacketData.Value.of(1),
                                PacketData.Value.of(3),
                                PacketData.Value.of(1),
                                PacketData.Value.of(1))),
                Arguments.of("[[8,7,6]]",
                        Packet.of(
                                PacketData.List.of(
                                        PacketData.Value.of(8),
                                        PacketData.Value.of(7),
                                        PacketData.Value.of(6)))),
                Arguments.of("[[4,4],4,4,4]",
                        Packet.of(
                                PacketData.List.of(
                                        PacketData.Value.of(4),
                                        PacketData.Value.of(4)),
                                PacketData.Value.of(4),
                                PacketData.Value.of(4),
                                PacketData.Value.of(4))),
                Arguments.of("[[1],[2,3,4]]",
                        Packet.of(
                                PacketData.List.of(
                                        PacketData.Value.of(1)),
                                PacketData.List.of(
                                        PacketData.Value.of(2),
                                        PacketData.Value.of(3),
                                        PacketData.Value.of(4)))),
                Arguments.of("[]",
                        Packet.of()),
                Arguments.of("[[]]",
                        Packet.of(
                                PacketData.List.empty())),
                Arguments.of("[[[]]]",
                        Packet.of(
                                PacketData.List.of(
                                        PacketData.List.empty()))),
                Arguments.of("[1,[2,[3,[4,[5,6,7]]]],8,9]",
                        Packet.of(
                                PacketData.Value.of(1),
                                PacketData.List.of(
                                        PacketData.Value.of(2),
                                        PacketData.List.of(
                                                PacketData.Value.of(3),
                                                PacketData.List.of(
                                                        PacketData.Value.of(4),
                                                        PacketData.List.of(
                                                                PacketData.Value.of(5),
                                                                PacketData.Value.of(6),
                                                                PacketData.Value.of(7))))),
                                PacketData.Value.of(8),
                                PacketData.Value.of(9))));
    }

    static Stream<Arguments> compareToExamples() {
        return Stream.of(
                Arguments.of(Packet.parse("[1,1,3,1,1]"), Packet.parse("[1,1,5,1,1]"), true),
                Arguments.of(Packet.parse("[[1],[2,3,4]]"), Packet.parse("[[1],4]"), true),
                Arguments.of(Packet.parse("[9]"), Packet.parse("[[8,7,6]]"), false),
                Arguments.of(Packet.parse("[[4,4],4,4]"), Packet.parse("[[4,4],4,4,4]"), true),
                Arguments.of(Packet.parse("[7,7,7,7]"), Packet.parse("[7,7,7]"), false),
                Arguments.of(Packet.parse("[]"), Packet.parse("[3]"), true),
                Arguments.of(Packet.parse("[[[]]]"), Packet.parse("[[]]"), false),
                Arguments.of(Packet.parse("[1,[2,[3,[4,[5,6,7]]]],8,9]"), Packet.parse("[1,[2,[3,[4,[5,6,0]]]],8,9]"), false));
    }
}
