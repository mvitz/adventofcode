const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/13')

describe('2021 day 13', () => {
  const example = `\
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(17)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 13)

      const result = part1(input)

      expect(result).toBe(706)
    })
  })
})
