#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/10

class Point
  attr_reader :x, :y

  def initialize(x, y, vx, vy)
    @x = x
    @y = y
    @vx = vx
    @vy = vy
  end

  def move
    @x += @vx
    @y += @vy
    self
  end

  def self.parse(line)
    match = /^position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+), \s*(-?\d+)>$/.match(line)
    Point.new(match[1].to_i, match[2].to_i, match[3].to_i, match[4].to_i)
  end
end

def draw(points)
  min_x = points.map(&:x).min
  max_x = points.map(&:x).max
  min_y = points.map(&:y).min
  max_y = points.map(&:y).max

  picture =
    (max_y - min_y + 1).times.map { |_|
      (max_x - min_x + 1).times.map { |_| "." }
    }

  points.each do |point|
    picture[point.y - min_y][point.x - min_x] = "#"
  end

  picture.each do |line|
    puts line.join('')
  end
end

input = '10_input.txt'
x = 10727
#x = 20000

points = File.readlines(input, mode: 'r').
  map(&:strip).
  map { |line| Point.parse line }

x.times { |_| points.each(&:move) }

draw(points)
