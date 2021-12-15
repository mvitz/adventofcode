const { linesOf } = require('../../src/utils')

const part1 = input => {
  return solve(input, 1)
}

const part2 = input => {
  return solve(input, 5)
}

const solve = (input, expansion) => {
  const tile = Tile.parse(input).expand(expansion, expansion)

  const { start, end } = Map.fromTile(tile)
  aStar(start, end)

  return end.risk
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

class Map {
  constructor (nodes) {
    this.nodes = nodes
  }

  get height () {
    return this.nodes.length
  }

  get width () {
    return this.nodes[0].length
  }

  get start () {
    return this.nodes[0][0]
  }

  get end () {
    return this.nodes[this.height - 1][this.width - 1]
  }

  static fromTile (tile) {
    const nodes = Map.toNodes(tile.rows)

    // link all nodes
    nodes.forEach((row, y) => row.forEach((node, x) => {
      // top neighbour
      if (y > 0) {
        node.append(nodes[y - 1][x])
      }
      // right neighbour
      if (x < nodes.length - 1) {
        node.append(nodes[y][x + 1])
      }
      // bottom neighbour
      if (y < nodes.length - 1) {
        node.append(nodes[y + 1][x])
      }
      // left neighbour
      if (x > 0) {
        node.append(nodes[y][x - 1])
      }
    }))

    const map = new Map(nodes)
    map.start.risk = 0
    return map
  }

  static toNodes (rows) {
    return rows.map(row => row.map(node => new Node(node)))
  }
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

class Tile {
  constructor (rows) {
    this.rows = rows
  }

  expand (vertical, horizontal) {
    // expand horizontal
    const tileRow = []
    for (const row of this.rows) {
      const subRow = []
      for (let i = 0; i < horizontal; i++) {
        subRow.push(...row.map(risk => Tile.increaseRisk(risk, i)))
      }
      tileRow.push(subRow)
    }

    // expand vertically
    const rows = []
    for (let i = 0; i < vertical; i++) {
      rows.push(...tileRow.map(row => row.map(risk => Tile.increaseRisk(risk, i))))
    }

    return new Tile(rows)
  }

  toString () {
    return this.rows.map(row => row.join('')).join('\n')
  }

  static parse (input) {
    return new Tile(
      linesOf(input)
        .map(line => line.split('').map(Number)))
  }

  static increaseRisk (risk, increment) {
    const increasedRisk = risk + increment
    return increasedRisk > 9 ? increasedRisk - 9 : increasedRisk
  }
}

module.exports = { part1, part2 }
