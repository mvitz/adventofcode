const part1 = input => {
  const sea = input.split(',')
    .map(age => Number(age))
    .map(age => new Laternfish(age))

  for (let day = 0; day < 80; day++) {
    sea.forEach(fish => fish.nextDay(sea))
  }

  return sea.length
}

class Laternfish {
  constructor (initialAge) {
    this.age = initialAge
  }

  nextDay (sea) {
    if (this.age === 0) {
      sea.push(new Laternfish(8))
      this.age = 6
    } else {
      this.age--
    }
  }
}

module.exports = { part1 }
