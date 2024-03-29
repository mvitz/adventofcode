const { linesOf } = require('../utils')

const part1 = input => {
  return solve(input, 2)
}

const part2 = input => {
  return solve(input, 50)
}

const solve = (input, numberOfEnhancements) => {
  let [algorithm, image] = parse(input)
  for (let round = 0; round < numberOfEnhancements; round++) {
    image = algorithm.apply(image)
    algorithm = algorithm.flip()
  }
  return image.numberOfLightPixels
}

const parse = input => {
  const [algorithm, pixels] = input.split('\n\n')
  return [new Algorithm(algorithm), Image.parse(pixels)]
}

class Algorithm {
  constructor (mapping, flipped = false) {
    this.mapping = mapping
    this.flipped = flipped
  }

  get flips () {
    return this.mapping[0] === '#' && this.mapping[this.mapping.length - 1] === '.'
  }

  flip () {
    return new Algorithm(this.mapping, this.flips ? !this.flipped : this.flipped)
  }

  apply (image) {
    const pixels = []
    for (let y = -2; y < image.height + 1; y++) {
      const row = []
      for (let x = -2; x < image.width + 1; x++) {
        const pixelWithNeighbours = this.pixelWithNeighbours(image, x, y)
        const pixelIndex = Algorithm.toIndex(pixelWithNeighbours)
        const pixel = this.mapping[pixelIndex]
        row.push(pixel)
      }
      pixels.push(row)
    }
    return new Image(pixels)
  }

  pixelWithNeighbours (image, x, y) {
    return [
      [this.pixelAt(image, x - 1, y - 1), this.pixelAt(image, x, y - 1), this.pixelAt(image, x + 1, y - 1)],
      [this.pixelAt(image, x - 1, y + 0), this.pixelAt(image, x, y + 0), this.pixelAt(image, x + 1, y + 0)],
      [this.pixelAt(image, x - 1, y + 1), this.pixelAt(image, x, y + 1), this.pixelAt(image, x + 1, y + 1)]
    ]
  }

  pixelAt (image, x, y) {
    return image.pixelAt(x, y, this.flipped ? '#' : '.')
  }

  static toIndex (pixelWithNeighbours) {
    return parseInt(pixelWithNeighbours
      .map(row => row.map(pixel => pixel === '.' ? 0 : 1).join(''))
      .join(''), 2)
  }
}

class Image {
  constructor (pixels) {
    this.pixels = pixels
  }

  pixelAt (x, y, fallback) {
    if (y < 0 || y >= this.height) {
      return fallback
    }
    if (x < 0 || x >= this.width) {
      return fallback
    }
    return this.pixels[y][x]
  }

  get numberOfLightPixels () {
    return this.pixels.flatMap(row => row.filter(pixel => pixel === '#')).length
  }

  get height () {
    return this.pixels.length
  }

  get width () {
    return this.pixels[0].length
  }

  toString () {
    return this.pixels.map(row => row.join('')).join('\n')
  }

  static parse (input) {
    return new Image(linesOf(input).map(line => line.split('')))
  }
}

module.exports = { part1, part2 }
