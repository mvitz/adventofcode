const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/24')

describe('2021 day 24', () => {
  describe.skip('part 1', () => {
    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 24)

      const result = part1(input)

      expect(result).toBe(-2)
    })
  })
})
