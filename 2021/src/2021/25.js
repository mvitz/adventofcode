const { linesOf } = require('../utils')

const part1 = input => {
  let situation = Situation.parse(input)
  let steps = 0
  let newSituation

  while (true) {
    steps++
    newSituation = situation.move()
    if (newSituation.equals(situation)) {
      return steps
    } else {
      situation = newSituation
    }
  }
}

class Situation {
  constructor (rows) {
    this.rows = rows
  }

  move () {
    const newRows = []

    for (let y = 0; y < this.height; y++) {
      const newRow = []
      for (let x = 0; x < this.width; x++) {
        const current = this.rows[y][x]
        if (current === 'v') {
          newRow.push(current)
        } else if (current === '.') {
          const left = this.rows[y][(x === 0 ? this.width : x) - 1]
          if (left === '>') {
            newRow.push(left)
          } else {
            newRow.push(current)
          }
        } else if (current === '>') {
          const right = this.rows[y][x === (this.width - 1) ? 0 : (x + 1)]
          if (right === '.') {
            newRow.push(right)
          } else {
            newRow.push(current)
          }
        }
      }
      newRows.push(newRow)
    }

    const newRows2 = []
    for (let y = 0; y < this.height; y++) {
      const newRow = []
      for (let x = 0; x < this.width; x++) {
        const current = newRows[y][x]
        if (current === '>') {
          newRow.push(current)
        } else if (current === '.') {
          const top = newRows[(y === 0 ? this.height : y) - 1][x]
          if (top === 'v') {
            newRow.push(top)
          } else {
            newRow.push(current)
          }
        } else if (current === 'v') {
          const bottom = newRows[y === (this.height - 1) ? 0 : (y + 1)][x]
          if (bottom === '.') {
            newRow.push(bottom)
          } else {
            newRow.push(current)
          }
        }
      }
      newRows2.push(newRow)
    }

    return new Situation(newRows2)
  }

  get height () {
    return this.rows.length
  }

  get width () {
    return this.rows[0].length
  }

  equals (other) {
    return this.toString() === other.toString()
  }

  toString () {
    return this.rows.map(row => row.join('')).join('\n')
  }

  static parse (input) {
    return new Situation(
      linesOf(input).map(line => line.split('')))
  }
}

module.exports = { part1 }
