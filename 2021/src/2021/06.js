const part1 = input => {
  return solve(input, 80)
}

const part2 = input => {
  return solve(input, 256)
}

const solve = (input, days) => {
  const sea = input.split(',')
    .map(age => Number(age))
    .reduce((sea, fish) => sea.add(fish), new Sea())

  for (let day = 0; day < days; day++) {
    sea.nextDay()
  }

  return sea.populationSize
}

class Sea {
  constructor () {
    this.population = [0, 0, 0, 0, 0, 0, 0, 0, 0]
  }

  add (fish) {
    this.population[fish]++
    return this
  }

  nextDay () {
    this.population = [
      this.population[1],
      this.population[2],
      this.population[3],
      this.population[4],
      this.population[5],
      this.population[6],
      this.population[7] + this.population[0],
      this.population[8],
      this.population[0]
    ]
  }

  get populationSize () {
    return this.population
      .reduce((sum, fishByAge) => sum + fishByAge, 0)
  }
}

module.exports = { part1, part2 }
