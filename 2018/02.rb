#!/usr/bin/env ruby

box_ids = File.readlines('02_input.txt', mode: 'r')

class String
  def char_count
    result = Hash.new(0)
    self.each_char do |char|
      result[char] = result[char] + 1
    end
    result
  end

  def distance_to(other)
    distance = 0
    self.each_char.with_index do |char, index|
      distance = distance + 1 unless char == other[index]
    end
    distance
  end

  def common_letters(other)
    result = []
    self.each_char.with_index do |char, index|
      result << char if char == other[index]
    end
    result
  end
end

# Part 1
puts box_ids.map { |line|
  char_count = line.char_count
  two = if char_count.has_value? 2 then 1 else 0 end
  three = if char_count.has_value? 3 then 1 else 0 end
  [two, three]
}.reduce([0, 0]) { |memo, pair|
  [memo[0] + pair[0], memo[1] + pair[1]]
}.reduce(1, :*)

# Part 2
pair = box_ids.select { |id|
  distances = box_ids.map { |i| id.distance_to i }
  distances.include? 1
}
puts pair[0].common_letters(pair[1]).join ''
