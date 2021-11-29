const fs = require('fs')
const path = require('path')

const readPuzzleInput = (year, day, part) => {
  return fs
    .readFileSync(path.resolve(__dirname, `${year}/${('0' + day).slice(-2)}-${part}.txt`))
    .toString()
}

const linesOf = input => {
  return input
    .split('\n')
    .map(line => line.trim())
    .filter(line => line !== '')
}

module.exports = {
  linesOf,
  readPuzzleInput
}
