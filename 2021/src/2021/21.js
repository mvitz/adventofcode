const { linesOf } = require('../utils')

const part1 = input => {
  const startingPositions = parse(input)

  const board = new Board(startingPositions)
  const dice = new DeterministicDice()

  const game = new Game(board, 1000)

  while (!game.hasWinner) {
    const steps = dice.roll() + dice.roll() + dice.roll()
    game.makeTurn(steps)
  }

  return game.losingScore * dice.rolls
}

const part2 = input => {
  const startingPositions = parse(input)

  const board = new Board(startingPositions)

  const wins = [0, 0]

  let games = [[new Game(board, 21), 1]]
  while (games.length > 0) {
    const ongoingGames = []
    for (const [game, combinations] of games) {
      if (game.hasWinner) {
        wins[game.winner] += combinations
      } else {
        for (const [steps, possibilities] of [
          [3, 1],
          [4, 3],
          [5, 6],
          [6, 7],
          [7, 6],
          [8, 3],
          [9, 1]
        ]) {
          ongoingGames.push([game.copy().makeTurn(steps), combinations * possibilities])
        }
      }
    }
    games = ongoingGames
  }

  return wins
}

const parse = input =>
  linesOf(input).map(line => line.substring(line.length - 1)).map(Number)

class Game {
  constructor (board, winningScore) {
    this.board = board
    this.winningScore = winningScore
    this.score = [0, 0]
    this.currentPlayer = 0
  }

  get hasWinner () {
    return this.score.some(score => score >= this.winningScore)
  }

  makeTurn (steps) {
    const { board, score, currentPlayer } = this

    const playerPosition = board.movePawn(currentPlayer, steps)
    score[currentPlayer] += playerPosition

    this.currentPlayer = (currentPlayer + 1) % 2

    return this
  }

  get losingScore () {
    return Math.min(...this.score)
  }

  get winner () {
    return this.score[0] >= this.winningScore ? 0 : 1
  }

  copy () {
    const game = new Game(this.board.copy(), this.winningScore)
    game.score = [...this.score]
    game.currentPlayer = this.currentPlayer
    return game
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

  copy () {
    return new Board([...this.pawnPositions])
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

module.exports = { part1, part2 }
