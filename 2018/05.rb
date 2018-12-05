#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/5

class String
  def is_upcase?
    !!/[[:upper:]]/.match(self)
  end

  def swapcase
    if self.is_upcase? then self.downcase else self.upcase end
  end

  def remove_units(*units)
    self.chars.reject { |unit| units.include? unit }.join('')
  end

  def react
    self.chars.reduce([]) do |result, unit|
      last_unit = result.last

      if last_unit == unit.swapcase
        result.pop
      else
        result << unit
      end

      result
    end
  end
end

polymer = File.readlines('05_input.txt').first.strip

# Part 1
puts polymer
  .react
  .length

# Part 2
polymers = ('a'..'z').map do |unit|
  polymer.remove_units(unit, unit.upcase)
end

puts polymers
  .map(&:react)
  .map(&:length)
  .sort
  .first
