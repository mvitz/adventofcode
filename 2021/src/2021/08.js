const part1 = input => {
  const isOneFourSevenOrEight = digit =>
    [ONE, FOUR, SEVEN, EIGHT].some(d => d.numberOfSegments === digit.length)

  return input
    .map(entry => parseEntry(entry))
    .flatMap(([, output]) => output)
    .filter(isOneFourSevenOrEight)
    .length
}

const part2 = input => {
  return input
    .map(entry => decodeEntry(entry))
    .reduce((sum, number) => sum + number)
}

const decodeEntry = entry => {
  const [patterns, output] = parseEntry(entry)

  const segmentMappings = decodePatterns(patterns)

  return Number(output
    .map(digit => digit.map(segment => segmentMappings[segment]))
    .flatMap(decodedDigit => DIGITS.find(digit => digit.matches(decodedDigit)))
    .map(digit => digit.value)
    .map(digit => `${digit}`)
    .reduce((result, digit) => result + digit, ''))
}

const parseEntry = entry => {
  const [patterns, output] = entry.split(' | ')
  return [
    patterns.split(' ').map(toWireSegments),
    output.split(' ').map(toWireSegments)
  ]
}

const decodePatterns = patterns => {
  const [one] = find(ONE, patterns)
  const [four] = find(FOUR, patterns)
  const [seven] = find(SEVEN, patterns)
  const [eight] = find(EIGHT, patterns)

  const [a] = differenceOf(seven, one)

  const [six] = find(SIX, patterns)
    .filter(maybeSix => intersectionOf(one, maybeSix).length === 1)

  const [f] = intersectionOf(one, six)
  const [c] = one.filter(segment => segment !== f)

  const [three] = find(THREE, patterns)
    .filter(maybeThree => intersectionOf(seven, maybeThree).length === 3)

  const [d] = differenceOf(intersectionOf(three, four), one)

  const [b] = differenceOf(four, [c, d, f])

  const [zero] = find(ZERO, patterns)
    .filter(maybeZero => intersectionOf([d], maybeZero).length === 0)

  const [e] = differenceOf(zero, three)
    .filter(maybeE => maybeE !== b)

  const [g] = differenceOf(eight, [a, b, c, d, e, f])

  return { [a]: 'a', [b]: 'b', [c]: 'c', [d]: 'd', [e]: 'e', [f]: 'f', [g]: 'g' }
}

const intersectionOf = (a, b) => {
  return a.filter(x => b.includes(x))
}

const differenceOf = (a, b) => {
  return a.filter(x => !b.includes(x))
}

const find = (digit, patterns) => {
  return patterns.filter(pattern => pattern.length === digit.numberOfSegments)
}

const toWireSegments = input => {
  return input.split('').sort()
}

class Digit {
  constructor (value, segments) {
    this.value = value
    this.segments = segments
  }

  get numberOfSegments () {
    return this.segments.length
  }

  matches (segments) {
    return this.numberOfSegments === segments.length &&
      this.numberOfSegments === intersectionOf(this.segments, segments).length
  }
}

// move to static properties once I'm able to update eslint to 8.x
const ZERO = new Digit(0, ['a', 'b', 'c', 'e', 'f', 'g'])
const ONE = new Digit(1, ['c', 'f'])
const TWO = new Digit(2, ['a', 'c', 'd', 'e', 'g'])
const THREE = new Digit(3, ['a', 'c', 'd', 'f', 'g'])
const FOUR = new Digit(4, ['b', 'c', 'd', 'f'])
const FIVE = new Digit(5, ['a', 'b', 'd', 'f', 'g'])
const SIX = new Digit(6, ['a', 'b', 'd', 'e', 'f', 'g'])
const SEVEN = new Digit(7, ['a', 'c', 'f'])
const EIGHT = new Digit(8, ['a', 'b', 'c', 'd', 'e', 'f', 'g'])
const NINE = new Digit(9, ['a', 'b', 'c', 'd', 'f', 'g'])

const DIGITS = [ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE]

module.exports = { part1, part2 }
