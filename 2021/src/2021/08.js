const part1 = input => {
  return input
    .map(entry => entry.split(' | '))
    .flatMap(([, outputValue]) => outputValue.split(' '))
    .filter(digit => [
      NUMBER_OF_SEGMENTS_USED.ONE,
      NUMBER_OF_SEGMENTS_USED.FOUR,
      NUMBER_OF_SEGMENTS_USED.SEVEN,
      NUMBER_OF_SEGMENTS_USED.EIGHT].includes(digit.length))
    .length
}

const NUMBER_OF_SEGMENTS_USED = Object.freeze({
  ZERO: 6,
  ONE: 2,
  TWO: 5,
  THREE: 5,
  FOUR: 4,
  FIVE: 5,
  SIX: 6,
  SEVEN: 3,
  EIGHT: 7,
  NINE: 6
})

module.exports = { part1 }
