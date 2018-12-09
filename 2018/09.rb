#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/9

# 486 players; last marble is worth 70833 points

players = 486
last_marble = 70833

def play(players, last_marble)
  points = players.times.map {|x| 0 }

  marbles = [0]
  current_marble = 0

  (1..last_marble).each do |marble|
    if marble % 23 == 0
      current_player = marble % players
      points[current_player] += marble
      current_marble = current_marble - 7
      current_marble = marbles.length + current_marble if current_marble < 0
      points[current_player] += marbles.delete_at current_marble
    else
      current_marble = current_marble + 2
      current_marble -= marbles.length if current_marble > marbles.length
      marbles.insert(current_marble, marble)
    end
  end

  points
end

# Part 1
puts play(players, last_marble).reduce { |highscore, score| if highscore > score then highscore else score end }
