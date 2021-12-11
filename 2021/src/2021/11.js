const { linesOf } = require('../utils')

const part1 = input => {
  const cavern = Cavern.parseCavern(input)

  let numberOfFlashes = 0
  for (let step = 1; step < 101; step++) {
    numberOfFlashes += cavern.nextStep()
  }
  return numberOfFlashes
}

const part2 = input => {
  const cavern = Cavern.parseCavern(input)

  let step = 1
  while (cavern.octopuses.length !== cavern.nextStep()) {
    step++
  }
  return step
}

class Octopus {
  constructor (x, y, energy) {
    this.x = x
    this.y = y
    this.energy = energy
  }

  reset () {
    this.energy = 0
    this.hasFlashed = false
  }

  flash () {
    this.hasFlashed = true
  }

  get shouldFlash () {
    return this.energy > 9 && !this.hasFlashed
  }

  increaseEnergy () {
    this.energy++
  }
}

class Cavern {
  constructor (grid) {
    this.grid = grid
  }

  nextStep () {
    // 1. increase energy level of each octopus
    this.octopuses.forEach(octopus => octopus.increaseEnergy())

    // 2. trigger flash execution
    const numberOfFlashesInStep = this.flash(this.octopuses)

    // 3. reset all octopuses that flashed during round
    this.octopuses.filter(octopus => octopus.hasFlashed).forEach(octopus => octopus.reset())

    return numberOfFlashesInStep
  }

  flash (octopuses, numberOfPreviousFlashes = 0) {
    const flashingOctopuses = octopuses.filter(octopus => octopus.shouldFlash)
    if (octopuses.length === 0) {
      return numberOfPreviousFlashes
    }

    // flash
    flashingOctopuses.forEach(octopus => octopus.flash())

    // increase energy level of all adjacent octopuses
    const adjacentOctopuses = flashingOctopuses.flatMap(octopus => this.adjacentOctopusesFor(octopus))
    adjacentOctopuses.forEach(octopus => octopus.increaseEnergy())

    // trigger next flash execution
    return this.flash(
      [...new Set(adjacentOctopuses)],
      numberOfPreviousFlashes + flashingOctopuses.length)
  }

  adjacentOctopusesFor ({ x, y }) {
    return [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]]
      .map(([yd, xd]) => this.octopusAt(x + xd, y + yd))
      .filter(octopus => !!octopus)
  }

  octopusAt (x, y) {
    if (y < 0 || y === this.grid.length) {
      return undefined
    }
    if (x < 0 || y === this.grid[y].length) {
      return undefined
    }
    return this.grid[y][x]
  }

  get octopuses () {
    return this.grid.flatMap(row => row)
  }

  static parseCavern (input) {
    return new Cavern(
      linesOf(input)
        .map((row, y) =>
          row.split('')
            .map((cell, x) => new Octopus(x, y, Number(cell)))))
  }
}

module.exports = { part1, part2 }
