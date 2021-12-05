const part1 = lineSegments => {
  const lines = lineSegments
    .map(toLineSegment)
    .map(toLine)

  const diagram = lines
    .flatMap(line => line)
    .reduce((result, point) => {
      result[point] = (result[point] || 0) + 1
      return result
    }, {})

  return Object.entries(diagram)
    .map(([_, numberOfLinesThroughPoint]) => numberOfLinesThroughPoint)
    .filter(numberOfLinesThroughPoint => numberOfLinesThroughPoint > 1)
    .length
}

const toLine = ([[x1, y1], [x2, y2]]) => {
  const result = []
  if (x1 === x2) {
    // vertical line
    const [start, end] = [y1, y2].sort((a, b) => a - b)
    for (let i = start; i <= end; i++) {
      result.push([x1, i])
    }
  } else if (y1 === y2) {
    // horizontal line
    const [start, end] = [x1, x2].sort((a, b) => a - b)
    for (let i = start; i <= end; i++) {
      result.push([i, y1])
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

module.exports = { part1 }
