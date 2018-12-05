#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/5

class String
  def is_upcase?
    !!/[[:upper:]]/.match(self)
  end

  def is_downcase?
    !self.is_upcase?
  end
end

polymer = File.readlines('05_input.txt').first.strip
resulting_polymer = polymer.chars.reduce do |result, unit|
  last_unit = result.chars.last

  current = unit
  if unit.is_upcase?
    current = unit.downcase
  else
    current = unit.upcase
  end

  if last_unit == current
    result[0...-1]
  else
    result + unit
  end
end

puts resulting_polymer.length
