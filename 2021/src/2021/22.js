const { linesOf } = require('../utils')

const part1 = input => {
  return solve(input, point => point >= -50 && point <= 50)
}

const part2 = input => {
  return solve(input, _ => true)
}

const solve = (input, filter) => {
  const reactorCore = new ReactorCore()

  parseInput(input, filter).forEach(([action, cubes]) =>
    cubes.forEach(cube => reactorCore.turn(action, cube)))

  return reactorCore.onCubes.size
}

const parseInput = (input, filter) => {
  return linesOf(input).map(line => {
    const [action, cuboid] = line.split(' ')
    const cubes = parseCuboid(cuboid, filter)
    return [action, cubes]
  })
}

const parseCuboid = (cuboid, filter) => {
  const [xr, yr, zr] = cuboid.split(',')
    .map(coordinate => parseCoordinate(coordinate, filter))

  const cubes = []
  xr.forEach(x => yr.forEach(y => zr.forEach(z => cubes.push([x, y, z]))))
  return cubes
}

const parseCoordinate = (input, filter) => {
  const [start, end] = input.substring(2).split('..').map(Number)
  const result = []
  for (let i = start; i <= end; i++) {
    result.push(i)
  }
  return result.filter(filter)
}

class ReactorCore {
  constructor () {
    this.onCubes = new Set()
  }

  turn (action, cube) {
    switch (action) {
      case 'on':
        this.turnOn(cube)
        break
      case 'off':
        this.turnOff(cube)
        break
    }
  }

  turnOn (cube) {
    this.onCubes.add(ReactorCore.asString(cube))
  }

  turnOff (cube) {
    this.onCubes.delete(ReactorCore.asString(cube))
  }

  static asString (cube) {
    return cube.join('|')
  }
}

module.exports = { part1, part2 }
