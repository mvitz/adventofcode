const part1 = input => {
  const [[tMinX, tMaxX], [tMaxY, tMinY]] = parseTarget(input)

  const xs = findPossibleXs(tMinX, tMaxX)
  const initialVelocities = findInitialVelocities(xs, tMinX, tMaxX, tMinY, tMaxY)

  return findMaxY(initialVelocities)
}

const findMaxY = initialVelocities => initialVelocities
  .map(([, y]) => y / 2 * (y + 1))
  .sort((a, b) => a - b)
  .reverse()[0]

const findInitialVelocities = (xs, tMinX, tMaxX, tMinY, tMaxY) => {
  const initialVelocities = []
  // TODO: find a way to calculate the upper bound
  for (const x of xs) {
    for (let y = tMaxY; y < 10_000; y++) {
      if (hits(x, y, tMinX, tMaxX, tMinY, tMaxY)) {
        initialVelocities.push([x, y])
      }
    }
  }
  return initialVelocities
}

const hits = (cX, cY, tMinX, tMaxX, tMinY, tMaxY) => {
  let [x, y] = [0, 0]
  while (x <= tMaxX && y >= tMaxY) {
    x += Math.max(0, cX--)
    y += cY--

    if (x >= tMinX && x <= tMaxX && y <= tMinY && y >= tMaxY) {
      return true
    }
  }
  return false
}

const findPossibleXs = (tMinX, tMaxX) => {
  const possibleXs = []
  for (let x = tMinX; x <= tMaxX; x++) {
    possibleXs.push(x)
  }
  for (let x = Math.round(tMaxX / 2); (x / 2 * (x + 1)) >= tMinX; x--) {
    possibleXs.push(x)
  }
  return possibleXs
}

const parseTarget = input => {
  const [, coordinates] = input.split(': ')
  return coordinates.split(', ').map(parseRange)
}

const parseRange = input => input
  .substr(2)
  .split('..')
  .map(Number)
  .sort((a, b) => a - b)

module.exports = { part1 }
