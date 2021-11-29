const part1 = input => {
  return input
    .map(line => entryFor(line))
    .map(entry => validPassword(entry))
    .filter(valid => valid)
    .length
}

const validPassword = ({min, max, char, password}) => {
  const characters = characterOccurences(password);
  return characters[char] >= min && characters[char] <= max
}

const entryFor = (line) => {
  const [policyInput, password] = line.split(': ')
  return {
    password,
    ...policyOf(policyInput)
  }
}

const policyOf = (input) => {
  const [occurences, char] = input.split(' ')
  const [min, max] = occurences.split('-')
  return { char, min: Number(min), max: Number(max) }
}

const characterOccurences = string => {
  return string
    .split('')
    .reduce((accumulator, char) => {
      accumulator[char] = (accumulator[char] || 0) + 1
      return accumulator
    }, {})
}

module.exports = { part1 }
