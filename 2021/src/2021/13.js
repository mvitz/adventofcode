const { linesOf } = require('../utils')

const part1 = input => {
  const [paper, [firstInstruction]] = parseInput(input)

  return paper
    .fold(firstInstruction)
    .numberOfDots
}

const part2 = input => {
  const [paper, instructions] = parseInput(input)

  return instructions
    .reduce((p, instruction) => p.fold(instruction), paper)
    .print()
}

const parseInput = input => {
  const [dotsInput, instructionsInput] = input.split('\n\n')
  const paper = Paper.parsePaper(dotsInput)
  const instructions = parseInstructions(instructionsInput)
  return [paper, instructions]
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

  print () {
    const [height, width] = this.dots
      .reduce(([maxY, maxX], { y, x }) =>
        [Math.max(maxY, y), Math.max(maxX, x)], [0, 0])
      .map(number => number + 1)

    let result = ''
    for (let y = 0; y < height; y++) {
      for (let x = 0; x < width; x++) {
        if (this.dots.some(dot => dot.equals(new Dot([x, y])))) {
          result += '#'
        } else {
          result += '.'
        }
      }
      result += '\n'
    }
    return result
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

module.exports = { part1, part2 }
