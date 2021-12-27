const { linesOf } = require('../utils')

const part1 = input => {
  const reactorCore = new ReactorCore()

  parseInput(input).forEach(([action, cubes]) =>
    cubes.forEach(cube => reactorCore.turn(action, cube)))

  return reactorCore.onCubes.size
}

const parseInput = input => {
  return linesOf(input).map(line => {
    const [action, cuboid] = line.split(' ')
    const cubes = parseCuboid(cuboid)
    return [action, cubes]
  })
}

const parseCuboid = cuboid => {
  const [xr, yr, zr] = cuboid.split(',').map(parseCoordinate)

  const cubes = []
  xr.forEach(x => yr.forEach(y => zr.forEach(z => cubes.push([x, y, z]))))
  return cubes
}

const parseCoordinate = input => {
  const [start, end] = input.substring(2).split('..').map(Number)
  const result = []
  for (let i = start; i <= end; i++) {
    result.push(i)
  }
  return result
    .filter(point => point >= -50 && point <= 50)
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

module.exports = { part1 }
