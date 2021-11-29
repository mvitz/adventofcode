const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2020/1')

describe('fix expense report', () => {

  const example = `\
1721
979
366
299
675
1456`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)
        .map(line => Number(line))

      const result = part1(input, 2)

      expect(result).toBe(514579)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2020, 1, 1))
        .map(line => Number(line))

      const result = part1(input, 2)

      expect(result).toBe(800139)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = linesOf(example)
        .map(line => Number(line))

      const result = part2(input, 3)

      expect(result).toBe(241861950)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2020, 1, 1))
        .map(line => Number(line))

      const result = part2(input, 3)

      expect(result).toBe(59885340)
    })
  })
})
