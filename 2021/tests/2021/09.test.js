const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/09')

describe('2021 day 9', () => {
  const example = `\
2199943210
3987894921
9856789892
8767896789
9899965678`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(15)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 9)

      const result = part1(input)

      expect(result).toBe(530)
    })
  })
})
