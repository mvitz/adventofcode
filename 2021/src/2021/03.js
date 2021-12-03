const part1 = report => {
  return gammaRateOf(report) * epsilonRateOf(report)
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

module.exports = { part1 }
