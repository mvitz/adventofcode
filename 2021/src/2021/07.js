const part1 = input => {
  return solve(input, (crab, pos) => Math.abs(crab - pos))
}

const part2 = input => {
  return solve(input, (crab, pos) => fact(Math.abs(crab - pos)))
}

const solve = (input, fuelStrategy) => {
  const crabs = input.split(',')
    .map(position => Number(position))
    .sort((a, b) => a - b)

  const min = crabs[0]
  const max = crabs.reverse()[0]

  const allSolutions = []
  for (let pos = min; pos <= max; pos++) {
    const fuelUsed = crabs.map(crab => fuelStrategy(crab, pos))
      .reduce((sum, fuel) => sum + fuel, 0)
    allSolutions.push({ pos, fuelUsed })
  }

  const { fuelUsed } = allSolutions.reduce((solution, candidate) => {
    if (candidate.fuelUsed < solution.fuelUsed) {
      return candidate
    } else {
      return solution
    }
  }, { fuelUsed: Number.POSITIVE_INFINITY })

  return fuelUsed
}

const fact = number => {
  let result = 0
  for (let i = 1; i <= number; i++) {
    result += i
  }
  return result
}

module.exports = { part1, part2 }
