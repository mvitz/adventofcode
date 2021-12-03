const part1 = report => {
  return gammaRateOf(report) * epsilonRateOf(report)
}

const part2 = report => {
  return oxygenGeneratorRatingOf(report) * co2ScrubberRating(report)
}

const oxygenGeneratorRatingOf = report => {
  let positions = report
  let round = 0
  while (positions.length > 1) {
    positions = analyze2(positions, mostCommonBitInPosition, round++)
  }

  return parseInt(positions[0], 2)
}

const co2ScrubberRating = report => {
  let positions = report
  let round = 0
  while (positions.length > 1) {
    positions = analyze2(positions, leastCommonBitInPosition, round++)
  }

  return parseInt(positions[0], 2)
}

const analyze2 = (report, strategy, i = 0) => {
  const bar = strategy(report, i)
  return report.map(toBits)
    .filter(bits => bits[i] === bar)
    .map(toBinaryNumber)
}

const mostCommonBitInPosition = (report, position) => {
  const bitsInPosition = analyse(report)[position]
    .flatMap(bits => bits)
  return bitsInPosition.filter(bit => bit === 1).length >= report.length / 2 ? 1 : 0
}

const leastCommonBitInPosition = (report, position) => {
  const bitsInPosition = analyse(report)[position]
    .flatMap(bits => bits)
  return bitsInPosition.filter(bit => bit === 1).length < report.length / 2 ? 1 : 0
}

const gammaRateOf = report => {
  const gammaRateBits = analyse(report)
    .map(bitsInPosition => {
      return bitsInPosition.filter(bit => bit === 1).length > report.length / 2 ? 1 : 0
    })
    .reduce((bits, mostCommonBit) => bits + mostCommonBit, '')

  return parseInt(gammaRateBits, 2)
}

const epsilonRateOf = report => {
  const epsilonRateBits = analyse(report)
    .map(bitsInPosition => {
      return bitsInPosition.filter(bit => bit === 1).length < report.length / 2 ? 1 : 0
    })
    .reduce((bits, mostCommonBit) => bits + mostCommonBit, '')

  return parseInt(epsilonRateBits, 2)
}

const analyse = report => {
  return report
    .map(toBits)
    .reduce((result, bits) => {
      bits.forEach((bit, i) => (result[i] = result[i] || []).push(bit))
      return result
    }, [])
}

const toBits = binaryNumber => {
  return binaryNumber
    .split('')
    .map(number => Number(number))
}

const toBinaryNumber = bits => {
  return bits.reduce((binaryNumber, bit) => binaryNumber + bit, '')
}

module.exports = { part1, part2 }
