const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1 } = require('../../src/2021/16')

describe('2021 day 16', () => {
  describe('part 1', () => {
    it.each`
    transmission                        | sumOfAllPackageVersions
    ${'8A004A801A8002F478'}             | ${'16'}
    ${'620080001611562C8802118E34'}     | ${'12'}
    ${'C0015000016115A2E0802F182340'}   | ${'23'}
    ${'A0016C880162017C3686B18A3D4780'} | ${'31'}
    `('should solve example $transmission => $sumOfAllPackageVersions', ({ transmission, sumOfAllPackageVersions }) => {
      const input = transmission

      const result = part1(input)

      expect(result).toBe(Number(sumOfAllPackageVersions))
    })

    it('should solve my input', () => {
      const [input] = linesOf(readPuzzleInput(2021, 16))

      const result = part1(input)

      expect(result).toBe(917)
    })
  })
})
