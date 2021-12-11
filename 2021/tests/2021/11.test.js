const { readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/11')

describe('2021 day 11', () => {
  const example = `\
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(1656)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 11)

      const result = part1(input)

      expect(result).toBe(1642)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = example

      const result = part2(input)

      expect(result).toBe(195)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 11)

      const result = part2(input)

      expect(result).toBe(320)
    })
  })
})
