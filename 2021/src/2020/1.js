const part1 = (expenseReport) => {
  return fixExpenseReport(expenseReport, 2)
}

const part2 = (expenseReport) => {
  return fixExpenseReport(expenseReport, 3)
}

const fixExpenseReport = (expenseReport, size) => {
  return combinations(expenseReport, size)
    .filter(combination => sum(combination) === 2020)
    .map(combination => product(combination))[0]
}

const combinations = (candidates, size, entry = []) => {
  if (size === 0) {
    return [entry]
  }

  const result = []
  for (let i = 0; i < candidates.length; i++) {
    const newEntry = [...entry, candidates[i]]
    result.push(...combinations(candidates.slice(i + 1), size - 1, newEntry))
  }
  return result
}

const sum = (array) => {
  return array.reduce((accumulator, entry) => accumulator + entry, 0)
}

const product = (array) => {
  return array.reduce((accumulator, entry) => accumulator * entry, 1)
}

module.exports = { part1, part2 }
