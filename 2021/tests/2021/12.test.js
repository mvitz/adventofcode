const { readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/12')

describe('2021 day 12', () => {
  const example = `\
start-A
start-b
A-c
A-b
b-d
A-end
b-end`

  const slightlyLargerExample = `\
dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc`

  const evenLargerExample = `\
fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW`

  describe('part 1', () => {
    it('should solve example', () => {
      const input = example

      const result = part1(input)

      expect(result).toBe(10)
    })

    it('should solve slightly larger example', () => {
      const input = slightlyLargerExample

      const result = part1(input)

      expect(result).toBe(19)
    })

    it('should solve even larger example', () => {
      const input = evenLargerExample

      const result = part1(input)

      expect(result).toBe(226)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 12)

      const result = part1(input)

      expect(result).toBe(3450)
    })
  })

  describe('part 2', () => {
    it('should solve example', () => {
      const input = example

      const result = part2(input)

      expect(result).toBe(36)
    })

    it('should solve slightly larger example', () => {
      const input = slightlyLargerExample

      const result = part2(input)

      expect(result).toBe(103)
    })

    it('should solve even larger example', () => {
      const input = evenLargerExample

      const result = part2(input)

      expect(result).toBe(3509)
    })

    it('should solve my input', () => {
      const input = readPuzzleInput(2021, 12)

      const result = part2(input)

      expect(result).toBe(96528)
    })
  })
})
