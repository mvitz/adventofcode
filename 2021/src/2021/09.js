const { linesOf } = require('../utils')

const part1 = input => {
  const heightmap = Heightmap.parseHeightmap(input)
  return heightmap
    .locations
    .filter(location => heightmap.isLowPoint(location))
    .map(riskLevel)
    .reduce(sum)
}

const riskLevel = ({ height }) => 1 + height

const sum = (result, number) => result + number

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

module.exports = { part1 }
