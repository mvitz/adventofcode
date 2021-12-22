const { readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/20')

describe('2021 day 20', () => {
  const example = `\
..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

#..#.
#....
##..#
..#..
..###`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(35)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 20)

      const result = part1(input)

      expect(result).toBe(5179)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = example

      const result = part2(input)

      expect(result).toBe(3351)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 20)

      const result = part2(input)

      expect(result).toBe(16112)
    })
  })
})
