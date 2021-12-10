const { linesOf } = require('../utils')

const part1 = input => {
  return Heightmap
    .parseHeightmap(input)
    .lowPoints
    .map(riskLevel)
    .reduce(sum)
}

const part2 = input => {
  return Heightmap
    .parseHeightmap(input)
    .basins
    .sort((first, second) => second.length - first.length)
    .slice(0, 3)
    .map(basin => basin.length)
    .reduce(product)
}

const riskLevel = ({ height }) => 1 + height

const sum = (result, number) => result + number
const product = (result, number) => result * number

class Location {
  constructor (x, y, height) {
    this.x = x
    this.y = y
    this.height = height
  }

  isLowerThan ({ height }) {
    return this.height < height
  }
}

class Heightmap {
  constructor (locations) {
    this.rows = locations
  }

  get lowPoints () {
    return this.locations
      .filter(location => this.isLowPoint(location))
  }

  get basins () {
    return this
      .lowPoints
      .map(lowPoint => this.basinFrom(lowPoint))
  }

  basinFrom (lowPoint) {
    const basin = []

    let basinAdjacentPoints = [lowPoint]
    while (basinAdjacentPoints.length) {
      basin.push(...new Set(basinAdjacentPoints))
      basinAdjacentPoints = basinAdjacentPoints
        .flatMap(location => this.adjacentLocationsOf(location))
        .filter(({ height }) => height < 9)
        .filter(location => !basin.includes(location))
    }

    return basin
  }

  get locations () {
    return this.rows.flatMap(row => row)
  }

  isLowPoint (point) {
    return this.adjacentLocationsOf(point)
      .every(adjacentLocation => point.isLowerThan(adjacentLocation))
  }

  location (x, y) {
    return this.rows[y][x]
  }

  adjacentLocationsOf ({ x, y }) {
    const adjacentLocations = []

    if (x > 0) { // has left adjacent location
      adjacentLocations.push(this.location(x - 1, y))
    }

    if (x + 1 < this.width) { // has right adjacent location
      adjacentLocations.push(this.location(x + 1, y))
    }

    if (y > 0) { // has top adjacent location
      adjacentLocations.push(this.location(x, y - 1))
    }

    if (y + 1 < this.height) { // has bottom adjacent location
      adjacentLocations.push(this.location(x, y + 1))
    }

    return adjacentLocations
  }

  get width () {
    return this.rows[0].length
  }

  get height () {
    return this.rows.length
  }

  static parseHeightmap (input) {
    return new Heightmap(
      linesOf(input)
        .map((line, y) => line
          .split('')
          .map((height, x) => new Location(x, y, Number(height)))))
  }
}

module.exports = { part1, part2 }
