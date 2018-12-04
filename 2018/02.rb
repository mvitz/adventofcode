#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/2

box_ids = File.readlines('02_input.txt', mode: 'r').map(&:strip)

class String
  def char_count
    self.chars
      .group_by(&:itself)
      .transform_values(&:count)
  end

  def distance_to(other)
    self.length - self.common_letters(other).length
  end

  def common_letters(other)
    self.chars.each_with_index.reduce([]) do |memo, (char, index)|
      memo << char if char == other[index]
      memo
    end
  end
end

# Part 1
puts box_ids.map { |line|
  char_count = line.char_count
  two = if char_count.has_value? 2 then 1 else 0 end
  three = if char_count.has_value? 3 then 1 else 0 end
  [two, three]
}.reduce([0, 0]) { |(m2, m3), (p2, p3)|
  [m2 + p2, m3 + p3]
}.reduce(1, :*)

# Part 2
(first, second) = box_ids.select do |id|
  distances = box_ids.map { |i| id.distance_to i }
  distances.include? 1
end
puts first.common_letters(second).join ''
