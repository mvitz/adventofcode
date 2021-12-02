const part1 = course => {
  const finalPosition = course
    .map(entry => entry.split(' '))
    .map(command => {
      switch (command[0]) {
        case COMMAND.DOWN:
          return { depth: +Number(command[1]), horizontal: 0 }
        case COMMAND.FORWARD:
          return { depth: 0, horizontal: Number(command[1]) }
        case COMMAND.UP:
          return { depth: -Number(command[1]), horizontal: 0 }
        default:
          throw new Error(`Found unknown command ${command[0]}`)
      }
    })
    .reduce((position, action) => {
      return {
        depth: position.depth + action.depth,
        horizontal: position.horizontal + action.horizontal
      }
    }, { depth: 0, horizontal: 0 })
  return finalPosition.depth * finalPosition.horizontal
}

const part2 = course => {
  return -1
}

const COMMAND = {
  DOWN: 'down',
  FORWARD: 'forward',
  UP: 'up'
}

module.exports = { part1, part2 }
