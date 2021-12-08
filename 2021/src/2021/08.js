const part1 = input => {
  return input
    .map(parseEntry)
    .flatMap(([, outputValueDigits]) => outputValueDigits)
    .filter(isOneFourSevenOrEight)
    .length
}

const part2 = input => {
  return input
    .map(parseEntry)
    .map(decodeEntry)
    .reduce(sum)
}

const parseEntry = entry => entry
  .split(' | ')
  .map(parseDigits)

const parseDigits = digits => digits
  .split(' ')
  .map(parseDigit)

const parseDigit = digit => digit
  .split('')
  .sort()

const isOneFourSevenOrEight = digit =>
  isOne(digit) || isFour(digit) || isSeven(digit) || isEight(digit)

const isOne = digit => digit.length === 2

const isFour = digit => digit.length === 4

const isSeven = digit => digit.length === 3

const isEight = digit => digit.length === 7

const decodeEntry = ([signalPatternDigits, outputValueDigits]) => {
  const digits = decodePatterns(signalPatternDigits)
  const decodedOutputDigits = decodeDigits(outputValueDigits, digits)
  return toNumber(decodedOutputDigits)
}

const decodePatterns = signalPatternDigits => {
  const one = signalPatternDigits
    .find(isOne)
  const four = signalPatternDigits
    .find(isFour)
  const seven = signalPatternDigits
    .find(isSeven)
  const eight = signalPatternDigits
    .find(isEight)
  const three = signalPatternDigits
    .find(digit => isThree(digit, one))
  const six = signalPatternDigits
    .find(digit => isSix(digit, one))
  const nine = signalPatternDigits
    .find(digit => isNine(digit, three))
  const zero = signalPatternDigits
    .find(digit => isZero(digit, six, nine))
  const five = signalPatternDigits
    .find(digit => isFive(digit, six))
  const two = signalPatternDigits
    .find(digit => isTwo(digit, three, five))

  return {
    [zero]: 0,
    [one]: 1,
    [two]: 2,
    [three]: 3,
    [four]: 4,
    [five]: 5,
    [six]: 6,
    [seven]: 7,
    [eight]: 8,
    [nine]: 9
  }
}

const isZeroSixOrNine = digit => digit.length === 6

const isTwoThreeOrFive = digit => digit.length === 5

const isZero = (digit, six, nine) =>
  isZeroSixOrNine(digit) && digit !== six && digit !== nine

const isTwo = (digit, three, five) =>
  isTwoThreeOrFive(digit) && digit !== three && digit !== five

const isThree = (digit, one) =>
  isTwoThreeOrFive(digit) && commonSegmentsOf(digit, one).length === 2

const isFive = (digit, six) =>
  isTwoThreeOrFive(digit) && commonSegmentsOf(digit, six).length === 5

const isSix = (digit, one) =>
  isZeroSixOrNine(digit) && commonSegmentsOf(digit, one).length === 1

const isNine = (digit, three) =>
  isZeroSixOrNine(digit) && commonSegmentsOf(digit, three).length === 5

const commonSegmentsOf = (first, second) =>
  first.filter(segment => second.includes(segment))

const decodeDigits = (digits, alphabet) => digits
  .map(digit => alphabet[digit])

const toNumber = digits => Number(digits.join(''))

const sum = (result, digit) => result + digit

module.exports = { part1, part2 }
