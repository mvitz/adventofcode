#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/11

def power_level(serial_number, cell)
  rack_id = cell.first + 10
  power_level = rack_id * cell.last
  power_level += serial_number
  power_level *= rack_id
  power_level = power_level.to_s[-3].to_i
  power_level -= 5
  power_level
end

#puts power_level( 8, [  3,   5])
#puts power_level(57, [122,  79])
#puts power_level(39, [217, 196])
#puts power_level(71, [101, 153])

def largest_total_power_grid(serial_number)
  grid = (1..300).map do |y|
    (1..300).map do |x|
      power_level(serial_number, [x, y])
    end
  end

  power_to_grid = []
  (0..297).each do |y|
    (0..297).each do |x|
      power = 3.times.map { |vx|
        3.times.map do |vy|
          grid[y + vy][x + vx]
        end
      }.flatten.reduce(:+)
      power_to_grid << [power, [x + 1, y + 1]]
    end
  end

  power_to_grid.sort_by { |item| item.first }.last
end

#puts largest_total_power_grid(18).inspect
#puts largest_total_power_grid(42).inspect

# Part 1
puts largest_total_power_grid(9221).inspect
