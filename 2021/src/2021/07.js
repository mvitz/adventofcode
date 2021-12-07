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

const factCache = [0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55]

const fact = number => {
  if (factCache.length >= number) {
    return factCache[number]
  }

  const lastCachedNumber = factCache.length - 1

  let result = factCache[lastCachedNumber]
  for (let i = lastCachedNumber + 1; i <= number; i++) {
    result += i
    factCache.push(result)
  }
  return result
}

module.exports = { part1, part2 }
