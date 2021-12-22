const { linesOf } = require('../utils')

const part1 = input => {
  const startingPositions = parse(input)

  const board = new Board(startingPositions)
  const dice = new DeterministicDice()

  const game = new Game(board, dice)

  while (!game.hasWinner) {
    game.makeTurn()
  }

  return game.losingScore * dice.rolls
}

const parse = input =>
  linesOf(input).map(line => line.substring(line.length - 1)).map(Number)

class Game {
  constructor (board, dice) {
    this.board = board
    this.dice = dice
    this.score = [0, 0]
    this.currentPlayer = 0
  }

  get hasWinner () {
    return this.score.some(score => score >= 1000)
  }

  makeTurn () {
    const { board, dice, score, currentPlayer } = this

    const steps = dice.roll() + dice.roll() + dice.roll()
    const playerPosition = board.movePawn(currentPlayer, steps)

    score[currentPlayer] += playerPosition

    this.currentPlayer = (currentPlayer + 1) % 2
  }

  get losingScore () {
    return Math.min(...this.score)
  }
}

class Board {
  constructor (startPositions) {
    this.pawnPositions = startPositions
  }

  movePawn (player, steps) {
    const position = this.pawnPositions[player]
    const newPosition = ((position + steps - 1) % 10) + 1
    this.pawnPositions[player] = newPosition
    return newPosition
  }
}

class DeterministicDice {
  constructor () {
    this.nextSide = 1
    this.rolls = 0
  }

  roll () {
    this.rolls++
    if (this.nextSide > 100) {
      this.nextSide = 1
    }
    return this.nextSide++
  }
}

module.exports = { part1 }
