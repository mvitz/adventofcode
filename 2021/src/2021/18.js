const { linesOf } = require('../utils')

const part1 = input => {
  const numbers = linesOf(input)
    .map(parseSnailfishNumber)

  console.log(add([1, 2], [[3, 4], 5]))
  console.log(reduce([[[[[9, 8], 1], 2], 3], 4]))

  return numbers
}

const add = (first, second) => {
  const number = [first, second]
  return reduce(number)
}

const reduce = number => {
}

// eslint-disable-next-line no-eval
const parseSnailfishNumber = input => eval(input)

module.exports = { part1 }
