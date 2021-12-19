const part1 = input => {
  const targetArea = parseTarget(input)

  const initialVelocities = findInitialVelocities(targetArea)

  return findMaxY(initialVelocities)
}

const part2 = input => {
  const targetArea = parseTarget(input)

  const initialVelocities = findInitialVelocities(targetArea)

  return initialVelocities.length
}

const findMaxY = initialVelocities => initialVelocities
  .map(([, y]) => y / 2 * (y + 1))
  .sort((a, b) => b - a)[0]

const findInitialVelocities = targetArea =>
  findPossibleXs(targetArea)
    .flatMap(x =>
      findPossibleYs(targetArea)
        .filter(y => hits(x, y, targetArea))
        .map(y => [x, y]))

const hits = (cX, cY, targetArea) => {
  let [x, y] = [0, 0]
  while (x <= targetArea.right && y >= targetArea.bottom) {
    x += Math.max(0, cX--)
    y += cY--

    if (targetArea.contains(x, y)) {
      return true
    }
  }
  return false
}

const findPossibleXs = ({ left, right }) => {
  const possibleXs = []
  for (let x = left; x <= right; x++) {
    possibleXs.push(x)
  }
  // max distance for X can be calculated via gauss summation and must be behind left border
  for (let x = Math.round(right / 2); (x / 2 * (x + 1)) >= left; x--) {
    possibleXs.push(x)
  }
  return possibleXs
}

const findPossibleYs = ({ bottom }) => {
  const ys = []
  for (let y = bottom; y <= Math.abs(bottom); y++) {
    ys.push(y)
  }
  return ys
}

const parseTarget = input => {
  const [, coordinates] = input.split(': ')
  return new Area(coordinates.split(', ').map(parseRange))
}

const parseRange = input => input
  .substr(2)
  .split('..')
  .map(Number)
  .sort((a, b) => a - b)

class Area {
  constructor ([x, y]) {
    this.x = x
    this.y = y
  }

  contains (x, y) {
    return x >= this.left && x <= this.right &&
      y >= this.bottom && y <= this.top
  }

  get left () {
    return this.x[0]
  }

  get right () {
    return this.x[1]
  }

  get bottom () {
    return this.y[0]
  }

  get top () {
    return this.y[1]
  }
}

module.exports = { part1, part2 }
