const { linesOf } = require('../../src/utils')

const part1 = input => {
  const rows = linesOf(input)
    .map(line => line.split('').map(Number).map(node => new Node(node)))
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
  start.value = 0

  const end = rows[rows.length - 1][rows[0].length - 1]

  aStar(start, end)

  return end.value
}

const aStar = (start, end) => {
  const open = [start]
  const closed = new Set()

  let current
  do {
    [current] = open
      .sort((first, second) => first.value - second.value)
      .splice(0, 1)

    if (current === end) {
      return
    }

    closed.add(current)

    current.successors.forEach(successor => {
      if (closed.has(successor)) {
        return
      }

      const cost = current.value + successor.value
      if (open.includes(successor) && cost >= successor.value) {
        return
      }

      successor.value = cost
      if (!open.includes(successor)) {
        open.push(successor)
      }
    })
  } while (current)

  throw new Error('No path found')
}

class Node {
  constructor (value) {
    this.value = value
    this.successors = []
  }

  append (node) {
    this.successors.push(node)
  }
}

module.exports = { part1 }
