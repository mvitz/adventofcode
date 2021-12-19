const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/19')

describe('2021 day 19', () => {
  const example = `\
[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]`

  describe.skip('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(4140)
    })

    it.skip('should solve my input', () => {
      const input = readPuzzleInput(2021, 18)

      const result = part1(input)

      expect(result).toBe(4095)
    })
  })
})
