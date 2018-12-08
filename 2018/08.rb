#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/8

input = File.readlines('08_input.txt').first.strip
#input = '2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2'
tree = input.split(' ').map(&:to_i)

class Node
  attr_accessor :children, :metadata

  def sum_of_metadata
    self.metadata.reduce(:+) + self.children.map(&:sum_of_metadata).reduce(0, :+)
  end

  def has_children?
    !self.children.empty?
  end

  def value
    return self.metadata.reduce(0, :+) unless self.has_children?

    self.metadata
      .map { |metadata| self.children[metadata - 1] }
      .reject(&:nil?)
      .map(&:value)
      .reduce(0, :+)
  end
end

def parse(tree)
  number_of_children = tree.shift
  number_of_metadata = tree.shift

  node = Node.new
  node.children = []

  number_of_children.times do |n|
    node.children << parse(tree)
  end

  node.metadata = []
  number_of_metadata.times do |n|
    node.metadata << tree.shift
  end

  node
end

parent = parse(tree)

# Part 1
puts parent.sum_of_metadata

# Part 2
puts parent.value
