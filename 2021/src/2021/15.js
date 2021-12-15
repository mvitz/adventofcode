const { linesOf } = require('../../src/utils')

const part1 = input => {
  const tile = parseTile(input)
  const [start, end] = toMap(toNodes(tile))
  aStar(start, end)
  return end.risk
}

const part2 = input => {
  const tile = parseTile(input)
  const tiles = expandTile(tile, 5, 5)

  const [start, end] = toMap(toNodes(tiles))
  aStar(start, end)
  return end.risk
}

const expandTile = (tile, vertical, horizontal) => {
  // expand horizontal
  const tileRow = []
  for (const row of tile) {
    const subRow = []
    for (let i = 0; i < horizontal; i++) {
      subRow.push(...row.map(risk => increaseRisk(risk, i)))
    }
    tileRow.push(subRow)
  }

  // expand vertically
  const rows = []
  for (let i = 0; i < vertical; i++) {
    rows.push(...tileRow.map(row => row.map(risk => increaseRisk(risk, i))))
  }

  return rows
}

const toMap = rows => {
  rows.forEach((row, y) => row.forEach((node, x) => {
    // top neighbour
    if (y > 0) {
      node.append(rows[y - 1][x])
    }
    // right neighbour
    if (x < row.length - 1) {
      node.append(rows[y][x + 1])
    }
    // bottom neighbour
    if (y < rows.length - 1) {
      node.append(rows[y + 1][x])
    }
    // left neighbour
    if (x > 0) {
      node.append(rows[y][x - 1])
    }
  }))

  const start = rows[0][0]
  start.risk = 0

  const end = rows[rows.length - 1][rows[0].length - 1]
  return [start, end]
}

const aStar = (start, end) => {
  const open = [start]
  const closed = new Set()

  let current
  do {
    [current] = open
      .sort((first, second) => first.risk - second.risk)
      .splice(0, 1)

    if (current === end) {
      return
    }

    closed.add(current)

    current.successors.forEach(successor => {
      if (closed.has(successor)) {
        return
      }

      const cost = current.risk + successor.risk
      if (open.includes(successor) && cost >= successor.risk) {
        return
      }

      successor.risk = cost
      if (!open.includes(successor)) {
        open.push(successor)
      }
    })
  } while (current)

  throw new Error('No path found')
}

class Node {
  constructor (risk) {
    this.risk = risk
    this.successors = []
  }

  append (node) {
    this.successors.push(node)
  }
}

const toNodes = rows =>
  rows.map(row => row.map(node => new Node(node)))

const increaseRisk = (risk, increment) => {
  const increasedRisk = risk + increment
  return increasedRisk > 9 ? increasedRisk - 9 : increasedRisk
}

const parseTile = input =>
  linesOf(input)
    .map(line => line.split('').map(Number))

module.exports = { part1, part2 }
