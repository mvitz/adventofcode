const part1 = report => {
  return solve(report, 1)
}

const part2 = report => {
  return solve(report, 3)
}

const DIRECTION = Object.freeze({
  DOWN: 'DOWN',
  STEADY: 'STEADY',
  UP: 'UP'
})

const solve = (report, windowSize) => {
  const windows = toWindows(report, windowSize)
    .map(window => windowSum(window))

  return analyze(windows)
    .filter(entry => entry === DIRECTION.DOWN)
    .length
}

const analyze = entries => {
  const result = []

  for (let i = 1; i < entries.length; i++) {
    const current = entries[i]
    const previous = entries[i - 1]

    const direction = toDirection(current, previous)

    result.push(direction)
  }

  return result
}

const toDirection = (current, last) => {
  if (current > last) {
    return DIRECTION.DOWN
  } else if (current < last) {
    return DIRECTION.UP
  } else {
    return DIRECTION.STEADY
  }
}

const toWindows = (report, windowSize) => {
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

module.exports = { part1, part2, windows: toWindows }
