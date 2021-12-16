const part1 = input => {
  const bits = toBinary(input)
  const packets = parse(bits)
  return sumOfAllPackageVersions(packets)
}

const part2 = input => {
  const bits = toBinary(input)
  const [packet] = parse(bits)
  return packet.eval()
}

const sumOfAllPackageVersions = packets => {
  let sum = 0
  for (const packet of packets) {
    sum += packet.version
    sum += sumOfAllPackageVersions(packet.subPackets || [])
  }
  return sum
}

const parse = bits => {
  const packets = []
  while (bits.length > 8) {
    packets.push(parsePacket(bits))
  }
  return packets
}

const parsePacket = bits => {
  const typeId = toNumber(bits.slice(3, 6))
  if (typeId === 4) {
    return LiteralValuePacket.parse(bits)
  } else {
    return OperatorPacket.parse(bits)
  }
}

const parsePackets = (bits, numberOfPacketsToParse) => {
  const packets = []
  for (let i = 0; i < numberOfPacketsToParse; i++) {
    packets.push(parsePacket(bits))
  }
  return packets
}

const toNumber = bits => parseInt(bits.join(''), 2)

const toBinary = input => {
  const lookup = {
    0: [0, 0, 0, 0],
    1: [0, 0, 0, 1],
    2: [0, 0, 1, 0],
    3: [0, 0, 1, 1],
    4: [0, 1, 0, 0],
    5: [0, 1, 0, 1],
    6: [0, 1, 1, 0],
    7: [0, 1, 1, 1],
    8: [1, 0, 0, 0],
    9: [1, 0, 0, 1],
    A: [1, 0, 1, 0],
    B: [1, 0, 1, 1],
    C: [1, 1, 0, 0],
    D: [1, 1, 0, 1],
    E: [1, 1, 1, 0],
    F: [1, 1, 1, 1]
  }

  return input.split('').flatMap(char => lookup[char])
}

class LiteralValuePacket {
  constructor (version, value) {
    this.version = version
    this.value = value
  }

  eval () {
    return this.value
  }

  toString () {
    return `{ ${this.version} | ${this.value} }`
  }

  static parse (bits) {
    const version = toNumber(bits.splice(0, 3))
    bits.splice(0, 3)

    const packetBits = []

    let group
    do {
      group = bits.splice(0, 5)
      packetBits.push(...group.slice(1))
    } while (group[0] !== 0)

    return new LiteralValuePacket(version, toNumber(packetBits))
  }
}

class OperatorPacket {
  constructor (version, type, subPackets) {
    this.version = version
    this.type = type
    this.subPackets = subPackets
  }

  eval () {
    const subValues = this.subPackets.map(packet => packet.eval())

    switch (this.type) {
      case 0: return subValues.reduce(sum)
      case 1: return subValues.reduce(product, 1)
      case 2: return subValues.sort((a, b) => a - b)[0]
      case 3: return subValues.sort((a, b) => a - b).reverse()[0]
      case 5: return subValues[0] > subValues[1] ? 1 : 0
      case 6: return subValues[0] < subValues[1] ? 1 : 0
      case 7: return subValues[0] === subValues[1] ? 1 : 0
      default: throw new Error(`Unknown operator with type ${this.type}`)
    }
  }

  toString () {
    return `{ ${this.version} | [ ${this.subPackets.join(', ')} ] }`
  }

  static parse (bits) {
    const version = toNumber(bits.splice(0, 3))
    const type = toNumber(bits.splice(0, 3))
    const subPackets = OperatorPacket.parseSubPackets(bits)
    return new OperatorPacket(version, type, subPackets)
  }

  static parseSubPackets (bits) {
    const [lengthTypeId] = bits.splice(0, 1)
    if (lengthTypeId === 0) {
      const totalBitLengthOfSubPackets = toNumber(bits.splice(0, 15))
      return parse(bits.splice(0, totalBitLengthOfSubPackets))
    } else {
      const numberOfSubPackets = toNumber(bits.splice(0, 11))
      return parsePackets(bits, numberOfSubPackets)
    }
  }
}

const sum = (result, number) => result + number
const product = (result, number) => result * number

module.exports = { part1, part2 }
