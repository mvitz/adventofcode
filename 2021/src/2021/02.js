const part1 = course => {
  return solve(course, day1Calculation)
}

const part2 = course => {
  return solve(course, day2Calculation)
}

const solve = (course, strategy) => {
  const { depth, horizontal } = course
    .map(line => toCommand(line))
    .reduce((position, action) => strategy(position, action), INITIAL_POSITION)
  return depth * horizontal
}

const toCommand = line => {
  const [action, value] = line.split(' ')
  return {
    action,
    value: Number(value)
  }
}

const INITIAL_POSITION = Object.freeze({
  aim: 0,
  depth: 0,
  horizontal: 0
})

const ACTION = Object.freeze({
  DOWN: 'down',
  FORWARD: 'forward',
  UP: 'up'
})

const day1Calculation = ({ depth, horizontal }, { action, value }) => {
  switch (action) {
    case ACTION.DOWN:
      return {
        depth: depth + value,
        horizontal
      }
    case ACTION.FORWARD:
      return {
        depth,
        horizontal: horizontal + value
      }
    case ACTION.UP:
      return {
        depth: depth - value,
        horizontal
      }
    default:
      throw new Error(`Found unknown action: ${action}`)
  }
}

const day2Calculation = ({ aim, depth, horizontal }, { action, value }) => {
  switch (action) {
    case ACTION.DOWN:
      return {
        aim: aim + value,
        depth,
        horizontal
      }
    case ACTION.FORWARD:
      return {
        aim,
        depth: depth + (aim * value),
        horizontal: horizontal + value
      }
    case ACTION.UP:
      return {
        aim: aim - value,
        depth,
        horizontal
      }
    default:
      throw new Error(`Found unknown action: ${action}`)
  }
}

module.exports = { part1, part2 }
