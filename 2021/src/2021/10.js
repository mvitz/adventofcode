const { linesOf } = require('../utils')

const part1 = navigationSubsystem => {
  return linesOf(navigationSubsystem)
    .map(line => findFirstIllegalCharacterIn(line))
    .filter(firstIllegalCharacterInLine => !!firstIllegalCharacterInLine)
    .map(toLineScore)
    .reduce(sum)
}

const findFirstIllegalCharacterIn = line => {
  const stack = []

  for (const token of line.split('')) {
    const closeToken = [')', ']', '}', '>'].findIndex(t => t === token)
    if (closeToken > -1 && closeToken !== stack.pop()) {
      return token
    }

    const openToken = ['(', '[', '{', '<'].findIndex(t => t === token)
    if (openToken > -1) {
      stack.push(openToken)
    }
  }

  return null
}

const SYNTAX_ERROR_SCORES = Object.freeze({
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137
})

const toLineScore = firstIllegalCharacter => SYNTAX_ERROR_SCORES[firstIllegalCharacter]

const sum = (result, number) => result + number

module.exports = { part1 }
