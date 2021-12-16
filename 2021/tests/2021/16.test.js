const { linesOf, readPuzzleInput } = require('../../src/utils')
const { part1, part2 } = require('../../src/2021/16')

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

  describe('part 2', () => {
    it.each`
    transmission                    | producedResult
    ${'C200B40A82'}                 | ${'3'}
    ${'04005AC33890'}               | ${'54'}
    ${'880086C3E88112'}             | ${'7'}
    ${'CE00C43D881120'}             | ${'9'}
    ${'D8005AC2A8F0'}               | ${'1'}
    ${'F600BC2D8F'}                 | ${'0'}
    ${'9C005AC2F8F0'}               | ${'0'}
    ${'9C0141080250320F1802104A08'} | ${'1'}
    `('should solve example $transmission => $producedResult', ({ transmission, producedResult }) => {
      const input = transmission

      const result = part2(input)

      expect(result).toBe(Number(producedResult))
    })

    it('should solve my input', () => {
      const [input] = linesOf(readPuzzleInput(2021, 16))

      const result = part2(input)

      expect(result).toBe(2536453523344)
    })
  })
})
