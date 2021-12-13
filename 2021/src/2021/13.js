const { linesOf } = require('../utils')

const part1 = input => {
  const [dotsInput, instructionsInput] = input.split('\n\n')
  const paper = Paper.parsePaper(dotsInput)
  const [firstInstruction] = parseInstructions(instructionsInput)

  const foldedPaper = paper.fold(firstInstruction)

  return foldedPaper.numberOfDots
}

const parseInstructions = input => {
  return input.split('\n').filter(line => line !== '').map(parseInstruction)
}

const parseInstruction = input => {
  const [,, instruction] = input.split(' ')
  const [direction, line] = instruction.split('=')
  return [direction, Number(line)]
}

class Paper {
  constructor (dots) {
    this.dots = dots
      .filter((dot, index, self) => self.findIndex(t => t.equals(dot)) === index)
      .sort((a, b) => a.compareTo(b))
  }

  get numberOfDots () {
    return this.dots.length
  }

  fold ([axis, line]) {
    if (axis === 'y') {
      const unchangedDots = this.dots
        .filter(dot => dot.isAbove(line))

      const foldedDots = this.dots
        .filter(dot => dot.isBelow(line))
        .map(({ x, y }) => new Dot([x, line - (y - line)]))

      return new Paper(unchangedDots.concat(foldedDots))
    }
    if (axis === 'x') {
      const unchangedDots = this.dots
        .filter(dot => dot.isLeftOf(line))

      const foldedDots = this.dots
        .filter(dot => dot.isRightOf(line))
        .map(({ x, y }) => new Dot([line - (x - line), y]))

      return new Paper(unchangedDots.concat(foldedDots))
    }
  }

  static parsePaper (input) {
    return new Paper(
      linesOf(input).map(line => Dot.parseDot(line)))
  }
}

class Dot {
  constructor ([x, y]) {
    this.y = y
    this.x = x
  }

  isAbove (line) {
    return this.y < line
  }

  isBelow (line) {
    return this.y > line
  }

  isLeftOf (line) {
    return this.x < line
  }

  isRightOf (line) {
    return this.x > line
  }

  compareTo ({ x, y }) {
    return this.y !== y ? this.y - y : this.x - x
  }

  equals ({ x, y }) {
    return this.x === x && this.y === y
  }

  static parseDot (input) {
    return new Dot(input.split(',').map(Number))
  }
}

module.exports = { part1 }
