const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/05')

describe('2021 day 5', () => {
  const example = `\
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part1(input)

      expect(result).toBe(5)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 5))

      const result = part1(input)

      expect(result).toBe(4421)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part2(input)

      expect(result).toBe(12)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 5))

      const result = part2(input)

      expect(result).toBe(18674)
    })
  })
})
