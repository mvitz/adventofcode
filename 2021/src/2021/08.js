const part1 = input => {
  return input
    .map(entry => entry.split(' | '))
    .flatMap(([, outputValue]) => outputValue.split(' '))
    .filter(digit => [
      ONE.numberOfSegments,
      FOUR.numberOfSegments,
      SEVEN.numberOfSegments,
      EIGHT.numberOfSegments].includes(digit.length))
    .length
}

class Digit {
  constructor (segments) {
    this.segments = segments
  }

  get numberOfSegments () {
    return this.segments.length
  }
}

// move to static properties once I'm able to update eslint to 8.x
const ONE = new Digit(['c', 'f'])
const FOUR = new Digit(['b', 'c', 'd', 'f'])
const SEVEN = new Digit(['a', 'c', 'f'])
const EIGHT = new Digit(['a', 'b', 'c', 'd', 'e', 'f', 'g'])

module.exports = { part1 }
