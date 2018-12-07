#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/7

def read_steps(instructions)
  instructions.reduce(Hash.new { |hash, key| hash[key] = [] }) do |steps, instruction|
    match = /^Step (\w) must be finished before step (\w) can begin.$/.match instruction

    step = match[2]
    requirement = match[1]

    requirements = steps[step]
    requirements << requirement

    steps[step] = requirements
    steps[requirement] = steps[requirement]

    steps
  end
end

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

# Part 1
steps = read_steps(instructions)
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

# Part 2
steps = read_steps(instructions)
step_durations = ('A'..'Z').each_with_index.map { |v, i| [v, 61 + i] }.to_h
#step_durations = ('A'..'Z').each_with_index.map { |v, i| [v,  1 + i] }.to_h
work = {}
workers = 5
#workers = 2
duration = 0

while steps.count > 0 or work.count > 0 do
#4.times do
  #puts "Second #{duration}"

  finished_steps = work.delete(0) || []
  #puts "finished_steps=#{finished_steps.inspect}"
  finished_steps.each do |step|
    steps.transform_values! do |requirements|
      requirements.delete step
      requirements
    end
  end
  #puts "steps=#{steps.inspect}"
  workers += finished_steps.length
  #puts "workers=#{workers}"

  next_steps = steps.select { |step, requirement| requirement.empty? }.keys.sort.take workers
  #puts "next_steps=#{next_steps.inspect}"
  next_steps.each { |step| steps.delete step }
  workers -= next_steps.length
  #puts "workers=#{workers}"

  next_steps.each do |step|
    step_duration = step_durations[step]
    work_for_duration = work[step_duration] || []
    work_for_duration << step
    work[step_duration] = work_for_duration
  end
  #puts "work=#{work.inspect}"

  work.transform_keys! { |remaining_duration| remaining_duration - 1 }
  #puts "work=#{work.inspect}"

  duration += 1
end

puts duration - 1
