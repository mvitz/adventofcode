const { linesOf } = require('../utils')

const part1 = navigationSubsystem => {
  return linesOf(navigationSubsystem)
    .map(line => findFirstIllegalCharacterIn(line))
    .filter(firstIllegalCharacterInLine => !!firstIllegalCharacterInLine)
    .map(toLineScore)
    .reduce(sum)
}

const part2 = navigationSubsystem => {
  const incompleteLine = line => !findFirstIllegalCharacterIn(line)

  const result = linesOf(navigationSubsystem)
    .filter(incompleteLine)
    .map(fixIncompleteLine)
    .map(toFixScore)
    .sort((a, b) => a - b)

  return result[(result.length - 1) / 2]
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

const fixIncompleteLine = line => {
  const stack = []

  for (const token of line.split('')) {
    const openToken = ['(', '[', '{', '<'].findIndex(t => t === token)
    if (openToken > -1) {
      stack.push(openToken)
    } else {
      const closeToken = [')', ']', '}', '>'].findIndex(t => t === token)
      if (closeToken > -1) {
        stack.pop()
      }
    }
  }

  const fixingClosingTokens = []
  for (const token of stack.reverse()) {
    const matchingCloseToken = [')', ']', '}', '>'][token]
    fixingClosingTokens.push(matchingCloseToken)
  }
  return fixingClosingTokens
}

const SYNTAX_ERROR_SCORES = Object.freeze({
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137
})

const toLineScore = firstIllegalCharacter => SYNTAX_ERROR_SCORES[firstIllegalCharacter]

const FIX_CHARACTER_SCORE = Object.freeze({
  ')': 1,
  ']': 2,
  '}': 3,
  '>': 4
})

const toFixScore = characters =>
  characters.reduce((score, character) => (score * 5) + FIX_CHARACTER_SCORE[character], 0)

const sum = (result, number) => result + number

module.exports = { part1, part2 }
