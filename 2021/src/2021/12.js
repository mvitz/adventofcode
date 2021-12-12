const { linesOf } = require('../utils')

const START = 'start'
const END = 'end'

const part1 = input => {
  const connections = parseConnections(input)
  const isValidPath = smallCavesVisitedAtLeastOnce

  return paths(START, END, connections, isValidPath).length
}

const part2 = input => {
  const connections = parseConnections(input)
  const isValidPath = path =>
    atMostVisitedSingleSmallCaveTwice(path) &&
      eachVisitedAtMostOnce(path, [START, END])

  return paths(START, END, connections, isValidPath).length
}

const paths = (from, to, connections, isValidPath, currentPath) => {
  if (!currentPath) {
    return paths(from, to, connections, isValidPath, [from])
  }

  const currentCave = currentPath[currentPath.length - 1]

  if (currentCave === to) {
    return [currentPath]
  }

  const nextPaths = connections[currentCave]
    .filter(nextCave => nextCave !== from)
    .map(nextCave => currentPath.concat(nextCave))
    .filter(isValidPath)

  if (nextPaths.length < 1) {
    return []
  } else {
    return nextPaths
      .flatMap(nextPath => paths(from, to, connections, isValidPath, nextPath))
  }
}

const smallCavesVisitedAtLeastOnce = path => {
  const smallCavesInPath = path.filter(isSmallCave)
  const visitedSmallCaves = new Set(smallCavesInPath)

  return smallCavesInPath.length === visitedSmallCaves.size
}

const atMostVisitedSingleSmallCaveTwice = path => {
  const smallCavesInPath = path.filter(isSmallCave)
  const visitedSmallCaves = new Set(smallCavesInPath)

  return smallCavesInPath.length === visitedSmallCaves.size || // no small cave visited twice
    smallCavesInPath.length === (visitedSmallCaves.size + 1) // single small cave visited twice
}

const eachVisitedAtMostOnce = (path, caves) =>
  new Set(path.filter(cave => caves.includes(cave))).size <= caves.length

const isSmallCave = cave => /^[a-z]*$/.test(cave)

const parseConnections = input =>
  toCaveSystem(
    linesOf(input).map(parseConnection))

const parseConnection = line => line.split('-')

const toCaveSystem = connections =>
  connections.reduce((caveSystem, [from, to]) => {
    putMulti(caveSystem, from, to)
    putMulti(caveSystem, to, from)
    return caveSystem
  }, {})

const putMulti = (map, key, value) => (map[key] = map[key] || []).push(value)

module.exports = { part1, part2 }
