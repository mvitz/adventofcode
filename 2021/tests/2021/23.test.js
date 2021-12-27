const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/23')

describe('2021 day 23', () => {
  const example = `\
#############
#...........#
###B#C#B#D###
  #A#D#C#A#
  #########`

  describe.skip('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(12521)
    })

    it.skip('should solve my input', () => {
      const input = readPuzzleInput(2021, 23)

      const result = part1(input)

      expect(result).toBe(-2)
    })
  })
})
