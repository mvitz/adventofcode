const part1 = input => {
  return solve(input,
    results => results.find(round => round[1].length > 0))
}

const part2 = input => {
  return solve(input,
    results => results.reverse().find(round => round[1].length > 0))
}

const solve = (input, winnerDetectionStrategy) => {
  const inputs = input
    .split('\n\n')
    .map(line => line.trim())

  const boards = inputs.splice(1)
    .map(Board.parse)

  const drawnNumbers = inputs[0]
    .split(',')
    .map(number => Number(number))

  const results = play(boards, drawnNumbers)
  const [numbersDrawnUntilWin, [firstWinnerInRound]] = winnerDetectionStrategy(results)
  return sumOfCells(firstWinnerInRound.allUnmarkedCells(numbersDrawnUntilWin)) * numbersDrawnUntilWin.slice(-1)[0]
}

const play = (boards, drawnNumbers) => {
  const results = []

  let boardsInPlay = boards
  let round = []
  for (const drawnNumber of drawnNumbers) {
    round = [...round, drawnNumber]

    const winners = winnersOfRound(boardsInPlay, round)
    results.push([round, winners])

    boardsInPlay = boardsInPlay.filter(board => !winners.includes(board))
  }

  return results
}

const winnersOfRound = (boards, drawnNumbers) => {
  return boards.filter(board => board.hasWon(drawnNumbers))
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
    for (const row of this.rows) {
      for (let colIndex = 0; colIndex < row.length; colIndex++) {
        const column = columns[colIndex] || []
        columns[colIndex] = column.concat(row[colIndex])
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

module.exports = { part1, part2 }
