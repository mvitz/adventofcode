const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/03')

describe('2021 day 3', () => {
  const example = `\
00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part1(input)

      expect(result).toBe(198)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 3))

      const result = part1(input)

      expect(result).toBe(1131506)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part2(input)

      expect(result).toBe(230)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 3))

      const result = part2(input)

      expect(result).toBe(7863147)
    })
  })
})
