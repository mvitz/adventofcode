const part1 = input => {
  const inputs = input
    .split('\n\n')
    .map(line => line.trim())

  const boards = inputs.splice(1)
    .map(Board.parse)

  const drawnNumbers = inputs[0]
    .split(',')
    .map(number => Number(number))

  const round = []
  for (const drawnNumber of drawnNumbers) {
    round.push(drawnNumber)
    const winner = boards.find(board => board.hasWon(round))
    if (winner) {
      return sumOfCells(winner.allUnmarkedCells(round)) * drawnNumber
    }
  }

  throw new Error('No winner found')
}

const sumOfCells = cells => {
  return cells.reduce((sum, cell) => sum + cell, 0)
}

class Board {
  constructor (rows) {
    this.rows = rows
  }

  hasWon (drawnNumbers) {
    return this.rows.some(row => Board.allMarked(row, drawnNumbers)) ||
        this.columns.some(column => Board.allMarked(column, drawnNumbers))
  }

  allUnmarkedCells (drawnNumbers) {
    return this.rows.flatMap(row => row)
      .filter(cell => !drawnNumbers.includes(cell))
  }

  get columns () {
    const columns = []
    for (let rowIndex = 0; rowIndex < this.rows.length; rowIndex++) {
      const row = this.rows[rowIndex]
      for (let colIndex = 0; colIndex < row.length; colIndex++) {
        (columns[colIndex] = columns[colIndex] || []).push(row[colIndex])
      }
    }
    return columns
  }

  static parse (input) {
    return new Board(input
      .split('\n')
      .map(Board.parseRow))
  }

  static parseRow (input) {
    return input
      .split(' ')
      .filter(cell => cell !== '')
      .map(Number)
  }

  static allMarked (rowOrCol, drawnNumbers) {
    return rowOrCol
      .every(cell => drawnNumbers.includes(cell))
  }
}

module.exports = { part1 }
