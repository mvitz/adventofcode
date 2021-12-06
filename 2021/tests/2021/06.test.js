const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/06')

describe('2021 day 6', () => {
  const example = '3,4,3,1,2'

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)[0]

      const result = part1(input)

      expect(result).toBe(5934)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 6))[0]

      const result = part1(input)

      expect(result).toBe(377263)
    })
  })
})
