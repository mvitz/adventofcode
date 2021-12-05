const part1 = lineSegments => {
  const lines = toLines(lineSegments)
    .filter(([first, second]) => isHorizontal(first, second) || isVertical(first, second))

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
  return Object.entries(diagram)
    .map(([_, numberOfLinesThroughPoint]) => numberOfLinesThroughPoint)
    .filter(numberOfLinesThroughPoint => numberOfLinesThroughPoint > 1)
}

const toDiagram = lines => {
  return lines
    .flatMap(line => line)
    .reduce((result, point) => {
      result[point] = (result[point] || 0) + 1
      return result
    }, {})
}

const toLines = lineSegments => {
  return lineSegments
    .map(toLineSegment)
    .map(toLine)
}

const toLine = ([p1, p2]) => {
  if (isVertical(p1, p2)) {
    const [[x, y1], [, y2]] = [p1, p2]
    return range(y1, y2).map(y => [x, y])
  } else if (isHorizontal(p1, p2)) {
    const [[x1, y], [x2]] = [p1, p2]
    return range(x1, x2).map(x => [x, y])
  } else {
    // is diagonal
    const [[x1, y1], [x2, y2]] = [p1, p2]

    const yc = range(y1, y2)

    return range(x1, x2).map((x, i) => [x, yc[i]])
  }
}

const isVertical = ([x1], [x2]) => {
  return x1 === x2
}

const isHorizontal = ([, y1], [, y2]) => {
  return y1 === y2
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

const toLineSegment = input => {
  return input
    .split(' -> ')
    .map(point => point.split(','))
    .map(([x, y]) => [Number(x), Number(y)])
}

module.exports = { part1, part2 }
