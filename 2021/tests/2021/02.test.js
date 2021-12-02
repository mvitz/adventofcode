const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/02')

describe('2020 day 2', () => {
  const example = `\
forward 5
down 5
forward 8
up 3
down 8
forward 2`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part1(input)

      expect(result).toBe(150)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 2, 1))

      const result = part1(input)

      expect(result).toBe(1580000)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part2(input)

      expect(result).toBe(900)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 2, 1))

      const result = part2(input)

      expect(result).toBe(1251263225)
    })
  })
})
