const { linesOf } = require('../utils')

const part1 = input => {
  const connections = linesOf(input)
    .map(parseConnection)
    .reduce((result, [from, to]) => {
      (result[from] = result[from] || []).push(to);
      (result[to] = result[to] || []).push(from)
      return result
    }, {})

  return paths('start', 'end', connections).length
}

const paths = (from, to, connections, currentPath) => {
  if (!currentPath) {
    return paths(from, to, connections, [from])
  }

  const currentCave = currentPath[currentPath.length - 1]

  if (currentCave === to) {
    return [currentPath]
  }

  const nextPaths = connections[currentCave]
    .filter(nextCave => nextCave !== from)
    .map(nextCave => currentPath.concat(nextCave))
    .filter(smallCavesVisitedAtLeastOnce)

  if (nextPaths.length < 1) {
    return []
  } else {
    return nextPaths.flatMap(nextPath => paths(from, to, connections, nextPath))
  }
}

const smallCavesVisitedAtLeastOnce = path => {
  const visitedSmallCaves = path.filter(isSmallCave)
  return visitedSmallCaves.length === new Set(visitedSmallCaves).size
}

const isSmallCave = cave => {
  return /^[a-z]*$/.test(cave)
}

const parseConnection = line => line.split('-')

module.exports = { part1 }
