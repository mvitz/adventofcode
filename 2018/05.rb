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

  def remove_last
    self[0...-1]
  end
end

def react(polymer)
  polymer.chars.reduce do |result, unit|
    last_unit = result.chars.last
    current = unit.swapcase

    if last_unit == current then result.remove_last else result + unit end
  end
end

polymer = File.readlines('05_input.txt').first.strip

# Part 1
resulting_polymer = react(polymer)
puts resulting_polymer.length
