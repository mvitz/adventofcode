#!/usr/bin/env ruby

# Part 1
frequency = File.readlines('input.txt', mode: 'r')
  .map(&:to_i)
  .reduce(:+)

puts "Part 1: #{frequency}"

# Part 2
frequencies = [0]
duplicated_frequency = nil

until duplicated_frequency
  File.open('input.txt', 'r') do |f|
    f.each_line do |change|
      frequency = frequencies.last + change.to_i
      unless duplicated_frequency
        duplicated_frequency = frequency if frequencies.include? frequency
        frequencies << frequency
      end
    end
  end
end

puts "Part 2: #{duplicated_frequency}"
