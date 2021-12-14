const { readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/14')

describe('2021 day 14', () => {
  const example = `\
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(1588)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 14)

      const result = part1(input)

      expect(result).toBe(3555)
    })
  })

  describe.skip('part 2', () => {
    it('should solve example', () => {
      const input = example

      const result = part2(input)

      expect(result).toBe(2188189693529)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 14)

      const result = part2(input)

      expect(result).toBe(-2)
    })
  })
})
