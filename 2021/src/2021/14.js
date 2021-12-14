const { linesOf } = require('../utils')

const part1 = input => {
  return solve(input, 10)
}

const part2 = input => {
  return solve(input, 40)
}

const solve = (input, rounds) => {
  let [polymer, rules] = parseInput(input)

  for (let i = 0; i < rounds; i++) {
    polymer = polymer.apply(rules)
  }

  const result = polymer.analyze()

  const elementOccurrences = Object.values(result)
    .sort((a, b) => a - b)

  return elementOccurrences[elementOccurrences.length - 1] - elementOccurrences[0]
}

class Polymer {
  constructor (elements) {
    this.elements = elements
  }

  apply (rules) {
    const newElements = []
    for (let i = 0; i < this.elements.length; i++) {
      const pair = new Pair(this.elements.slice(i, i + 2))

      newElements.push(pair.first)

      const insertion = rules.apply(pair)
      if (insertion) {
        newElements.push(insertion)
      }
    }
    return new Polymer(newElements)
  }

  analyze () {
    return this.elements
      .reduce((result, element) => {
        (result[element] = (result[element] || 0) + 1)
        return result
      }, {})
  }

  toString () {
    return this.elements.join('')
  }
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

class Rules {
  constructor () {
    this.rules = []
  }

  add (rule) {
    this.rules.push(rule)
    return this
  }

  apply (pair) {
    return (this.rules.find(rule => rule.matches(pair)) || {}).insertion
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

const parseTemplate = input => { return new Polymer(input.split('')) }

const parseRules = input =>
  linesOf(input)
    .map(line => line.split(' -> '))
    .map(([pair, insertion]) => new Rule(new Pair(pair.split('')), insertion))
    .reduce((rules, rule) => rules.add(rule), new Rules())

module.exports = { part1, part2 }
