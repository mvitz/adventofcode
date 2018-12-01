#!/usr/bin/env ruby

changes = File.readlines('input.txt', mode: 'r').map(&:to_i)

# Part 1
frequency = changes.reduce(:+)
puts "Part 1: #{frequency}"

# Part 2
require 'set'

frequency = 0
frequencies = Set[0]
duplicated_frequency = nil

until duplicated_frequency
  changes.each do |change|
    frequency = frequency + change
    unless frequencies.add? frequency
      duplicated_frequency = frequency
      break
    end
  end
end

puts "Part 2: #{duplicated_frequency}"
