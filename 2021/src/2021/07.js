const part1 = input => {
  const crabs = input.split(',')
    .map(position => Number(position))
    .sort((a, b) => a - b)

  const min = crabs[0]
  const max = crabs.reverse()[0]

  const allSolutions = []
  for (let i = min; i <= max; i++) {
    const fuelUsed = crabs.map(crab => Math.abs(crab - i))
      .reduce((sum, fuel) => sum + fuel, 0)
    allSolutions.push({ pos: i, fuelUsed })
  }

  const result = allSolutions.reduce((solution, candidate) => {
    if (candidate.fuelUsed < solution.fuelUsed) {
      return candidate
    } else {
      return solution
    }
  }, { fuelUsed: Number.POSITIVE_INFINITY })

  return result.fuelUsed
}

module.exports = { part1 }
