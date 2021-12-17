const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/17')

describe('2021 day 17', () => {
  const example = 'target area: x=20..30, y=-10..-5'

  describe('part 1', () => {
    it('should solve example', () => {
      const [input] = linesOf(example)

      const result = part1(input)

      expect(result).toBe(45)
    })

    it('should solve my input', () => {
      const [input] = linesOf(readPuzzleInput(2021, 17))

      const result = part1(input)

      expect(result).toBe(4095)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const [input] = linesOf(example)

      const result = part2(input)

      expect(result).toBe(112)
    })

    it('should solve my input', () => {
      const [input] = linesOf(readPuzzleInput(2021, 17))

      const result = part2(input)

      expect(result).toBe(3773)
    })
  })
})
