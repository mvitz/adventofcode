const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2020/02')

describe('check passwords', () => {
  describe('part 1', () => {
    it('should return 2 for example', () => {
      const input = linesOf(`
1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc`)

      const result = part1(input)

      expect(result).toBe(2)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2020, 2, 1))

      const result = part1(input)

      expect(result).toBe(550)
    })
  })
})
