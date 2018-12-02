#!/usr/bin/env ruby

box_ids = File.readlines('02_1_input.txt', mode: 'r')

# Part 1
class String
  def char_count
    result = Hash.new(0)
    self.each_char do |char|
      result[char] = result[char] + 1
    end
    result
  end
end

puts box_ids.map { |line|
  char_count = line.char_count
  two = if char_count.has_value? 2 then 1 else 0 end
  three = if char_count.has_value? 3 then 1 else 0 end
  [two, three]
}.reduce([0, 0]) { |memo, pair|
  [memo[0] + pair[0], memo[1] + pair[1]]
}.reduce(1, :*)
