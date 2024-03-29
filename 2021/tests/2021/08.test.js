const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/08')

describe('2021 day 8', () => {
  const example = `\
be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = linesOf(example)

      const result = part1(input)

      expect(result).toBe(26)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 8))

      const result = part1(input)

      expect(result).toBe(367)
    })
  })

  describe('part2', () => {
    it('should solve simple example', () => {
      const input = linesOf('acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf')

      const result = part2(input)

      expect(result).toBe(5353)
    })

    it('should solve example', () => {
      const input = linesOf(example)

      const result = part2(input)

      expect(result).toBe(61229)
    })

    it('should solve my input', () => {
      const input = linesOf(readPuzzleInput(2021, 8))

      const result = part2(input)

      expect(result).toBe(974512)
    })
  })
})
