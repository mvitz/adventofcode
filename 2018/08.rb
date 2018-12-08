#!/usr/bin/env ruby
#
# Solution for https://adventofcode.com/2018/day/8

input = File.readlines('08_input.txt').first.strip
#input = '2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2'
tree = input.split(' ').map(&:to_i)

class Node

  def initialize
    @children = []
    @metadata = []
  end

  def add_child(child)
    @children << child
  end

  def add_metadata(metadata)
    @metadata << metadata
  end

  def has_children?
    !@children.empty?
  end

  def sum_of_metadata
    metadata_value + @children.map(&:sum_of_metadata).reduce(0, :+)
  end

  def value
    return metadata_value unless has_children?

    @metadata
      .map { |metadata| @children[metadata - 1] }
      .reject(&:nil?)
      .map(&:value)
      .reduce(0, :+)
  end

  private

  def metadata_value
    @metadata.reduce(:+)
  end

end

def parse(tree)
  number_of_children = tree.shift
  number_of_metadata = tree.shift

  node = Node.new

  number_of_children.times do |n|
    node.add_child parse(tree)
  end

  number_of_metadata.times do |n|
    node.add_metadata tree.shift
  end

  node
end

parent = parse(tree)

# Part 1
puts parent.sum_of_metadata

# Part 2
puts parent.value
