const part1 = input => {
  const bits = toBinary(input)
  const packets = parse(bits)
  return packets.map(packet => packet.sumOfVersions).reduce((result, number) => result + number, 0)
}

const part2 = input => {
  const bits = toBinary(input)
  const [packet] = parse(bits)
  return packet.eval()
}

const parse = bits => {
  const packets = []
  while (bits.length > 8) {
    packets.push(Packet.parse(bits))
  }
  return packets
}

const parsePackets = (bits, numberOfPacketsToParse) => {
  const packets = []
  for (let i = 0; i < numberOfPacketsToParse; i++) {
    packets.push(Packet.parse(bits))
  }
  return packets
}

class Packet {
  constructor (version, type, value, subPackets = []) {
    this.version = version
    this.type = type
    this.value = value
    this.subPackets = subPackets
  }

  get sumOfVersions () {
    return this.version + this.subPackets
      .map(packet => packet.sumOfVersions)
      .reduce((result, number) => result + number, 0)
  }

  eval () {
    const subValues = this.subPackets.map(packet => packet.eval())

    switch (this.type) {
      case 0: return subValues.reduce((result, number) => result + number, 0)
      case 1: return subValues.reduce((result, number) => result * number, 1)
      case 2: return subValues.sort((a, b) => a - b)[0]
      case 3: return subValues.sort((a, b) => a - b).reverse()[0]
      case 4: return this.value
      case 5: return subValues[0] > subValues[1] ? 1 : 0
      case 6: return subValues[0] < subValues[1] ? 1 : 0
      case 7: return subValues[0] === subValues[1] ? 1 : 0
      default: throw new Error(`Unknown operator with type ${this.type}`)
    }
  }

  static parse (bits) {
    const version = toNumber(bits.splice(0, 3))
    const type = toNumber(bits.splice(0, 3))

    if (type === 4) {
      return new Packet(version, type, Packet.parseValue(bits))
    }

    const subPackets = Packet.parseSubPackets(bits)
    return new Packet(version, type, undefined, subPackets)
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

  static parseValue (bits) {
    const valueBits = []

    let group
    do {
      group = bits.splice(0, 5)
      valueBits.push(...group.slice(1))
    } while (group[0] !== 0)

    return toNumber(valueBits)
  }
}

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

const toNumber = bits => parseInt(bits.join(''), 2)

module.exports = { part1, part2 }
