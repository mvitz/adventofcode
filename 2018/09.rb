#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/9

# 486 players; last marble is worth 70833 points

players = 486
last_marble = 70833

class Node
  attr_accessor :value, :next, :previous

  def initialize(value)
    @value = value
  end

  def append(node)
    node.next = @next
    node.previous = self

    @next.previous = node
    @next = node

    node
  end

  def remove
    @previous.next = @next
    @next.previous = @previous
    @next
  end

end

def play(players, last_marble)
  points = players.times.map { |x| 0 }

  current = Node.new 0
  current.next = current
  current.previous = current

  (1..last_marble).each do |marble|
    if marble % 23 == 0
      7.times do
        current = current.previous
      end
      points[marble % players] += (marble + current.value)
      current = current.remove
    else
      current = current.next.append(Node.new(marble))
    end
  end

  points
end

# Part 1
puts play(players, last_marble).max

# Part 2
puts play(players, last_marble * 100).max
