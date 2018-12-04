#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/3

require 'set'

def parse_claim(line)
  match = /^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$/.match(line)
  {
    id: match[1],
    square: {
      left: match[2].to_i,
      top: match[3].to_i,
      right: match[2].to_i + match[4].to_i - 1,
      bottom: match[3].to_i + match[5].to_i - 1
    }
  }
end

def to_area(square)
  square[:left].upto(square[:right]).to_a
    .product(square[:top].upto(square[:bottom]).to_a)
    .map { |field| { x: field[0], y: field[1] } }
end

claims = File.readlines('03_input.txt', mode: 'r')
  .map(&:strip)
  .map { |line| parse_claim(line) }
  .map { |claim| claim.merge(area: to_area(claim[:square])) }

claimed_fields = claims
  .map { |claim|
    claim[:area].map { |coordinate| { id: claim[:id], coordinate: coordinate } }
  }.flatten(1)
  .reduce({}) { |result, field|
    coordinate = field[:coordinate]
    # TODO: merge is slow, why?
    #result.merge!(coordinate => Set[field[:id]]) do |key, oldval, newval|
    #  oldval.merge newval
    #end
    ids = result[coordinate] || Set[]
    ids << field[:id]
    result[coordinate] = ids
    result
  }

# Part 1
puts claimed_fields
  .select { |coordinate, ids| ids.count > 1 }
  .count

# Part 2
overlapping_claims = claimed_fields
  .select { |coordinate, ids| ids.count > 1 }
  .values
  .flatten(1)
  .reduce(Set[]) { |result, ids| result.merge ids }

puts claims
  .map { |claim| claim[:id] }
  .to_set
  .subtract(overlapping_claims)
  .to_a
  .first
