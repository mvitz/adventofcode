const part1 = report => {
  return solve(report, 1)
}

const part2 = report => {
  return solve(report, 3)
}

const solve = (report, windowSize) => {
  return windows(report, windowSize)
    .map(window => windowSum(window))
    .reduce((result, depth) => {
      return {
        increased: depth > result.lastDepth ? result.increased + 1 : result.increased,
        lastDepth: depth
      }
    }, { increased: 0, lastDepth: Number.POSITIVE_INFINITY })
    .increased
}

const windows = (report, windowSize) => {
  const result = []
  for (let i = 0; i < report.length + 1 - windowSize; i++) {
    const window = []
    for (let j = 0; j < windowSize; j++) {
      window.push(report[i + j])
    }
    result.push(window)
  }
  return result
}

const windowSum = window => {
  return window.reduce((sum, number) => sum + number, 0)
}

module.exports = { part1, part2, windows }
