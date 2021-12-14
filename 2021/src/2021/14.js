const { linesOf } = require('../utils')

const part1 = input => {
  let [polymer, rules] = parseInput(input)

  for (let i = 0; i < 10; i++) {
    polymer = apply(polymer, rules)
  }

  const result = analyze(polymer)

  const elementOccurrences = Object.values(result)
    .sort((a, b) => a - b)

  return elementOccurrences[elementOccurrences.length - 1] - elementOccurrences[0]
}

const analyze = polymer =>
  polymer.reduce((result, element) => {
    (result[element] = (result[element] || 0) + 1)
    return result
  }, {})

const apply = (template, rules) => {
  const newPolymer = []
  for (let i = 0; i < template.length; i++) {
    const pair = new Pair(template.slice(i, i + 2))

    newPolymer.push(pair.first)

    const { insertion } = rules.find(rule => rule.matches(pair)) || {}
    if (insertion) {
      newPolymer.push(insertion)
    }
  }
  return newPolymer
}

class Pair {
  constructor ([first, second]) {
    this.first = first
    this.second = second
  }

  equals ({ first, second }) {
    return this.first === first && this.second === second
  }
}

class Rule {
  constructor (pair, insertion) {
    this.pair = pair
    this.insertion = insertion
  }

  matches (pair) {
    return this.pair.equals(pair)
  }
}

const parseInput = input => {
  const [templateInput, rulesInput] = input.split('\n\n')
  return [parseTemplate(templateInput), parseRules(rulesInput)]
}

const parseTemplate = input => input.split('')

const parseRules = input =>
  linesOf(input)
    .map(line => line.split(' -> '))
    .map(([pair, insertion]) => new Rule(new Pair(pair.split('')), insertion))

module.exports = { part1 }
