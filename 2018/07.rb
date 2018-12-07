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

def process_steps(steps, workers = 1, step_durations = Hash.new(1))
  duration = -1

  steps_in_progress = {}
  finished_steps = []

  while steps.count > 0 or steps_in_progress.count > 0 do

    # Cleanup finished work
    turn_finished_steps = steps_in_progress.delete(0) || []
    turn_finished_steps.each do |step|
      steps.transform_values! do |requirements|
        requirements.delete step
        requirements
      end
    end
    workers += turn_finished_steps.length
    finished_steps += turn_finished_steps

    # Add new work
    next_steps = steps.select { |step, requirement| requirement.empty? }.keys.sort.take workers
    next_steps.each do |step|
      steps.delete step

      step_duration = step_durations[step]

      work_for_duration = steps_in_progress[step_duration] || []
      work_for_duration << step
      steps_in_progress[step_duration] = work_for_duration
    end
    workers -= next_steps.length

    # Do work
    steps_in_progress.transform_keys! { |remaining_duration| remaining_duration - 1 }
    duration += 1
  end

  [finished_steps.join(''), duration]
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
(order, _) = process_steps(steps)
puts order

# Part 2
workers = 5
#workers = 2

step_durations = ('A'..'Z').each_with_index.map { |v, i| [v, 61 + i] }.to_h
#step_durations = ('A'..'Z').each_with_index.map { |v, i| [v,  1 + i] }.to_h

steps = read_steps(instructions)
(_, duration) = process_steps(steps, workers, step_durations)
puts duration
