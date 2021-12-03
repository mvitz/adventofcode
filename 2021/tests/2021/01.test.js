const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2, windows } = require('../../src/2021/01')

describe('2021 day 1', () => {
  const example = `\
199
200
208
210
200
207
240
269
260
263`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)
        .map(line => Number(line))

      const result = part1(input)

      expect(result).toBe(7)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 1, 1))
        .map(line => Number(line))

      const result = part1(input)

      expect(result).toBe(1400)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = linesOf(example)
        .map(line => Number(line))

      const result = part2(input)

      expect(result).toBe(5)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 1, 1))
        .map(line => Number(line))

      const result = part2(input)

      expect(result).toBe(1429)
    })
  })

  it('windows should build correct windows', () => {
    expect(windows([1, 2, 3], 1)).toEqual([[1], [2], [3]])
    expect(windows([1, 2, 3], 2)).toEqual([[1, 2], [2, 3]])
    expect(windows([199, 200, 208, 210, 200, 207, 240], 3)).toEqual([[199, 200, 208], [200, 208, 210], [208, 210, 200], [210, 200, 207], [200, 207, 240]])
  })
})
