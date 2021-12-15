const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/15')

describe('2021 day 15', () => {
  const example = `\
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(40)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 15)

      const result = part1(input)

      expect(result).toBe(386)
    })
  })
})
