#!/usr/bin/env ruby

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

candidate = result
  .map { |k, v| { guard: k, sleep_period: v } }
  .sort_by { |r| [r[:sleep_period].count] }
  .last

period = candidate[:sleep_period]
  .reduce(Hash.new(0)) { |memo, t|
      memo[t] = memo[t] + 1
      memo
  }.map { |k, v| { k: k, v: v } }
  .sort_by { |r| [r[:v]] }
  .last[:k]

puts candidate[:guard] * period
