const part1 = lineSegments => {
  const lines = toLines(lineSegments)
    .filter(line => !line.isDiagonal)

  return solve(lines)
}

const part2 = lineSegments => {
  const lines = toLines(lineSegments)

  return solve(lines)
}

const solve = lines => {
  const diagram = toDiagram(lines)

  return overlappingPointsOf(diagram).length
}

const overlappingPointsOf = diagram => {
  return Object.values(diagram)
    .filter(numberOfLinesThroughPoint => numberOfLinesThroughPoint > 1)
}

const toDiagram = lines => {
  return lines
    .flatMap(line => line.points)
    .reduce((result, point) => {
      result[point] = (result[point] || 0) + 1
      return result
    }, {})
}

const toLines = lineSegments => {
  return lineSegments
    .map(toLineSegment)
    .map(([start, end]) => new Line(start, end))
}

const toLineSegment = input => {
  return input
    .split(' -> ')
    .map(point => point.split(','))
    .map(([x, y]) => [Number(x), Number(y)])
}

class Line {
  constructor (start, end) {
    this.start = start
    this.end = end
  }

  get isDiagonal () {
    return !this.isHorizontal && !this.isVertical
  }

  get isHorizontal () {
    const [[, y1], [, y2]] = [this.start, this.end]
    return y1 === y2
  }

  get isVertical () {
    const [[x1], [x2]] = [this.start, this.end]
    return x1 === x2
  }

  get points () {
    if (this.isHorizontal) {
      const [[x1, y], [x2]] = [this.start, this.end]
      return range(x1, x2).map(x => [x, y])
    } else if (this.isVertical) {
      const [[x, y1], [, y2]] = [this.start, this.end]
      return range(y1, y2).map(y => [x, y])
    } else { // diagonal
      const [[x1, y1], [x2, y2]] = [this.start, this.end]
      const yc = range(y1, y2)
      return range(x1, x2).map((x, i) => [x, yc[i]])
    }
  }
}

const range = (start, end) => {
  const result = []
  if (start < end) {
    for (let i = start; i <= end; i++) {
      result.push(i)
    }
  } else {
    for (let i = start; i >= end; i--) {
      result.push(i)
    }
  }
  return result
}

module.exports = { part1, part2 }
