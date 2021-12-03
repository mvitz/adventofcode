const part1 = report => {
  return gammaRateOf(report) * epsilonRateOf(report)
}

const gammaRateOf = report => {
  return analyze(report, mostCommonBitIn)
}

const epsilonRateOf = report => {
  return analyze(report, leastCommonBitIn)
}

const analyze = (numbers, strategy) => {
  const bits = groupBitsByPosition(numbers)
    .map(strategy)
  return toNumber(toBinaryNumber(bits))
}

const part2 = report => {
  return oxygenGeneratorRatingOf(report) * co2ScrubberRatingOf(report)
}

const oxygenGeneratorRatingOf = report => {
  return analyze2(report, mostCommonBitInPosition)
}

const co2ScrubberRatingOf = report => {
  return analyze2(report, leastCommonBitInPosition)
}

const analyze2 = (numbers, strategy, position = 0) => {
  if (numbers.length === 1) {
    return toNumber(numbers[0])
  }

  const bitResultInPosition = strategy(numbers, position)
  const remainingNumbers = numbers
    .map(toBits)
    .filter(bits => bits[position] === bitResultInPosition)
    .map(toBinaryNumber)
  return analyze2(remainingNumbers, strategy, position + 1)
}

const mostCommonBitInPosition = (numbers, position) => {
  const bitsInPosition = groupBitsByPosition(numbers)[position]
  return mostCommonBitIn(bitsInPosition)
}

const leastCommonBitInPosition = (numbers, position) => {
  const bitsInPosition = groupBitsByPosition(numbers)[position]
  return leastCommonBitIn(bitsInPosition)
}

const groupBitsByPosition = numbers => {
  return numbers
    .map(toBits)
    .reduce((bitsByPosition, bits) => {
      bits.forEach((bit, position) => (bitsByPosition[position] = bitsByPosition[position] || []).push(bit))
      return bitsByPosition
    }, [])
}

// returns an array that contains the number of 0 or 1 at the corresponding position
const analyseBits = bits => {
  return bits.reduce((statistic, bit) => {
    statistic[bit]++
    return statistic
  }, [0, 0])
}

const mostCommonBitIn = bits => {
  const [zeros, ones] = analyseBits(bits)
  return ones >= zeros ? 1 : 0
}

const leastCommonBitIn = bits => {
  const [zeros, ones] = analyseBits(bits)
  return ones < zeros ? 1 : 0
}

const toBits = binaryNumber => {
  return binaryNumber
    .split('')
    .map(number => Number(number))
}

const toBinaryNumber = bits => {
  return bits.reduce((binaryNumber, bit) => binaryNumber + bit, '')
}

const toNumber = binaryNumber => {
  return parseInt(binaryNumber, 2)
}

module.exports = { part1, part2 }
