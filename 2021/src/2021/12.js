const { linesOf } = require('../utils')

const part1 = input => {
  const connections = parseConnections(input)
  const isValidPath = smallCavesVisitedAtLeastOnce

  return paths('start', 'end', connections, isValidPath).length
}

const part2 = input => {
  const start = 'start'
  const connections = parseConnections(input)
  const isValidPath = path => {
    const lastCave = path[path.length - 1]
    if (lastCave === start) {
      // start can only be visited exactly only once
      return false
    }

    const visitedSmallCaves = path.filter(isSmallCave)
    const uniqueVisitedSmallCaves = new Set(visitedSmallCaves).size
    return visitedSmallCaves.length === uniqueVisitedSmallCaves || // no small cave visited twice
      visitedSmallCaves.length === uniqueVisitedSmallCaves + 1 // single small cave visited twice
  }

  return paths(start, 'end', connections, isValidPath).length
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
    return nextPaths.flatMap(nextPath => paths(from, to, connections, isValidPath, nextPath))
  }
}

const smallCavesVisitedAtLeastOnce = path => {
  const visitedSmallCaves = path.filter(isSmallCave)
  return visitedSmallCaves.length === new Set(visitedSmallCaves).size
}

const isSmallCave = cave => {
  return /^[a-z]*$/.test(cave)
}

const parseConnections = input =>
  linesOf(input)
    .map(parseConnection)
    .reduce((result, [from, to]) => {
      (result[from] = result[from] || []).push(to);
      (result[to] = result[to] || []).push(from)
      return result
    }, {})

const parseConnection = line => line.split('-')

module.exports = { part1, part2 }
