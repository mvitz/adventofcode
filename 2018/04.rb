#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/4

def parse_record(line)
  match = /^\[(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})\] (.*)$/.match(line)
  {
    timestamp: match[1..5].join('').to_i,
    minutes: match[5].to_i,
    description: match[6]
  }
end

#records = File.readlines('04_1_example.txt', mode: 'r')
records = File.readlines('04_input.txt', mode: 'r')
  .map(&:strip)
  .map { |line| parse_record(line) }
  .sort_by { |record| [record[:timestamp]] }

current_guard = nil
last_event = nil
result = {}

records.each do |record|
  description = record[:description]
  if /^Guard #(\d*) begins shift$/ =~ description
    current_guard = /^Guard #(\d*) begins shift$/.match(description)[1].to_i
  elsif /^falls asleep$/ =~ description
    last_event = record
  elsif /^wakes up$/ =~ description
    sleep_period = last_event[:minutes].upto(record[:minutes] - 1).to_a
    foo = result[current_guard] || []
    foo = foo.concat sleep_period
    result[current_guard] = foo
  end
end

# Part 1
candidate = result
  .map { |k, v| { guard: k, sleep_period: v } }
  .sort_by { |r| [r[:sleep_period].count] }
  .last

period = candidate[:sleep_period]
  .group_by(&:itself)
  .transform_values(&:count)
  .map { |k, v| { k: k, v: v } }
  .sort_by { |r| [r[:v]] }
  .last[:k]

puts candidate[:guard] * period

# Part 2
candidate = result
  .map { |k, v|
    {
      guard: k,
      sleep_period: v
        .group_by(&:itself)
        .transform_values(&:count)
        .map { |key, value| { k: key, v: value } }
        .sort_by { |r| [r[:v]] }
    }
  }
  .sort_by { |r| [r[:sleep_period].last[:v]] }
  .last

puts candidate[:guard] * candidate[:sleep_period].last[:k]
