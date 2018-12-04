#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/4

def parse_record(line)
  match = /^\[\d{4}-\d{2}-\d{2} \d{2}:(\d{2})\] (.*)$/.match(line)
  {
    minutes: match[1].to_i,
    description: match[2]
  }
end

#records = File.readlines('04_1_example.txt', mode: 'r')
records = File.readlines('04_input.txt', mode: 'r')
  .map(&:strip)
  .sort
  .map { |line| parse_record(line) }

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
  .sort_by { |guard, sleep_period| sleep_period.count }
  .last
  .each_slice(2)
  .to_h
  .first

period = candidate[1]
  .group_by(&:itself)
  .transform_values(&:count)
  .sort_by { |minute, count| count }
  .last
  .first

puts candidate[0] * period

# Part 2
candidate = result
  .transform_values { |sleep_period|
    sleep_period
      .group_by(&:itself)
      .transform_values(&:count)
      .sort_by { |minute, count| count }
  }
  .sort_by { |guard, sleep_period| sleep_period.last.last }
  .last

puts candidate[0] * candidate[1].last.first
