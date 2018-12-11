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

def grid(serial_number, size = 300)
  (1..size).map do |y|
    (1..size).map do |x|
      power_level(serial_number, [x, y])
    end
  end
end

def power_of_grid(grid, x, y, size, cache)
  if size == 1
    return grid[y][x]
  end

  power = cache[[x, y, size - 1]]
  unless power
    power = power_of_grid(grid, x, y, size - 1, cache)
  end
  (size - 1).times do |dy|
    power += grid[y + dy][x + size - 1]
  end
  power += grid[y + size - 1][x..(x + size - 1)].reduce(:+)
  power
end

def largest_power_grid(grid, min_size = 1, max_size = 300)
  grids_to_power = {}
  grid.each_with_index do |line, y|
    for x in 0 ... line.size
      min_size.upto(max_size).each do |size|
        if (x + size) <= line.size and (y + size) <= grid.size
          power = power_of_grid(grid, x, y, size, grids_to_power)
          grids_to_power[[x, y, size]] = power
        end
      end
    end
  end

  result = grids_to_power.sort_by { |k,v| v }.last.first
  "#{result[0] + 1},#{result[1] + 1},#{result[2]}"
end

# Part 1
puts largest_power_grid(grid(9221), 3, 3)

# Part 2
#puts largest_power_grid(grid(18))
#puts largest_power_grid(grid(42))
puts largest_power_grid(grid(9221))
