const { readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/21')

describe('2021 day 21', () => {
  const example = `\
Player 1 starting position: 4
Player 2 starting position: 8`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(739785)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 21)

      const result = part1(input)

      expect(result).toBe(598416)
    })
  })

  describe.skip('part 2', () => {
    it('should solve example', () => {
      const input = example

      const result = part2(input)

      expect(result).toBe(444356092776315)
    })

    it.skip('should solve my input', () => {
      const input = readPuzzleInput(2021, 21)

      const result = part2(input)

      expect(result).toBe(-2)
    })
  })
})
