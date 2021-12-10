const { readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/10')

describe('2021 day 10', () => {
  const example = `\
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(26397)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 10)

      const result = part1(input)

      expect(result).toBe(392367)
    })
  })
})
