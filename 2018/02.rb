#!/usr/bin/env ruby

box_ids = File.readlines('02_input.txt', mode: 'r').map(&:strip)

class String
  def char_count
    self.chars.reduce(Hash.new(0)) do |memo, char|
      memo[char] = memo[char] + 1
      memo
    end
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
}.reduce([0, 0]) { |memo, pair|
  [memo[0] + pair[0], memo[1] + pair[1]]
}.reduce(1, :*)

# Part 2
pair = box_ids.select { |id|
  distances = box_ids.map { |i| id.distance_to i }
  distances.include? 1
}
puts pair[0].common_letters(pair[1]).join ''
