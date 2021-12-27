const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/25')

describe('2021 day 25', () => {
  const example = `\
v...>>.vv>
.vv>>.vv..
>>.>v>...v
>>v>>.>.v.
v>v.vv.v..
>.>>..v...
.vv..>.>v.
v.v..>>v.v
....v..v.>`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(58)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 25)

      const result = part1(input)

      expect(result).toBe(321)
    })
  })
})
