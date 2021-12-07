const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/07')

describe('2021 day 7', () => {
  const example = '16,1,2,0,4,2,7,1,2,14'

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)[0]

      const result = part1(input)

      expect(result).toBe(37)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 7))[0]

      const result = part1(input)

      expect(result).toBe(349357)
    })
  })
})
