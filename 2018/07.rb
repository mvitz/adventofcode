#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/7

instructions = [
  'Step C must be finished before step A can begin.',
  'Step C must be finished before step F can begin.',
  'Step A must be finished before step B can begin.',
  'Step A must be finished before step D can begin.',
  'Step B must be finished before step E can begin.',
  'Step D must be finished before step E can begin.',
  'Step F must be finished before step E can begin.'
]
instructions = File.readlines('07_input.txt').map(&:strip)

steps = Hash.new { |hash, key| hash[key] = [] }
instructions.each do |instruction|
  match = /^Step (\w) must be finished before step (\w) can begin.$/.match instruction

  step = match[2]
  requirement = match[1]

  requirements = steps[step]
  requirements << requirement
  steps[step] = requirements

  steps[requirement] = steps[requirement]
end

# Part 1
solution = ''
while steps.count > 0 do
  next_steps = steps.select { |step, requirement| requirement.empty? }.keys
  next_step = next_steps.sort.first

  steps.delete next_step
  steps.transform_values! do |requirements|
    requirements.delete next_step
    requirements
  end

  solution += next_step
end

puts solution

