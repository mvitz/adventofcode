const { linesOf } = require('../utils')

const foo = (instructions, variables, f) => {
  const p = new Alu(instructions)
  for (let i = 9; i > 0; i--) {
    const v = p.execute(i, { ...variables })

    const result = f(v)
    if (result > -1) {
      return parseInt(`${i}${result}`)
    }
  }
  return -1
}

const part1 = input => {
  const instructionsPerDigitPosition = input
    .split('\ninp')
    .filter(part => part.length > 0)
    .map(part => linesOf('inp' + part).map(Instruction.parse))

  return foo(instructionsPerDigitPosition[0], { w: 0, x: 0, y: 0, z: 0 }, v1 =>
    foo(instructionsPerDigitPosition[1], v1, v2 =>
      foo(instructionsPerDigitPosition[2], v2, v3 =>
        foo(instructionsPerDigitPosition[3], v3, v4 =>
          foo(instructionsPerDigitPosition[4], v4, v5 =>
            foo(instructionsPerDigitPosition[5], v5, v6 =>
              foo(instructionsPerDigitPosition[6], v6, v7 =>
                foo(instructionsPerDigitPosition[7], v7, v8 =>
                  foo(instructionsPerDigitPosition[8], v8, v9 =>
                    foo(instructionsPerDigitPosition[9], v9, v10 =>
                      foo(instructionsPerDigitPosition[10], v10, v11 =>
                        foo(instructionsPerDigitPosition[11], v11, v12 =>
                          foo(instructionsPerDigitPosition[12], v12, v13 =>
                            foo(instructionsPerDigitPosition[13], v13, v14 => {
                              if (v14.z === 0) {
                                console.log('Found solution!!!!')
                                return 1
                              } else {
                                return -1
                              }
                            }))))))))))))))
}

class Alu {
  constructor (program) {
    // console.log(program)
    this.program = program
  }

  execute (inputs, variables = { w: 0, x: 0, y: 0, z: 0 }) {
    // console.log(inputs)
    // console.log(variables)
    this.program.forEach(instruction => instruction.execute(variables, inputs))
    // console.log(variables)
    return variables
  }

  static parse (input) {
    return new Alu()
  }
}

class Instruction {
  constructor (type, a, b) {
    this.type = type
    this.a = a
    this.b = b
  }

  execute (variables, input) {
    const { type, a, b } = this
    // console.log(type, a, b)

    switch (type) {
      case 'inp':
        this.inp(a, input, variables)
        break
      case 'add':
        this.add(a, b, variables)
        break
      case 'mul':
        this.mul(a, b, variables)
        break
      case 'div':
        this.div(a, b, variables)
        break
      case 'mod':
        this.mod(a, b, variables)
        break
      case 'eql':
        this.eql(a, b, variables)
        break
    }
    // console.log(variables)
  }

  inp (a, b, variables) {
    variables[a] = b
  }

  add (a, b, variables) {
    variables[a] += Instruction.read(b, variables)
  }

  mul (a, b, variables) {
    variables[a] *= Instruction.read(b, variables)
  }

  div (a, b, variables) {
    variables[a] = Math.floor(variables[a] / Instruction.read(b, variables))
  }

  mod (a, b, variables) {
    variables[a] = variables[a] % Instruction.read(b, variables)
  }

  eql (a, b, variables) {
    variables[a] = variables[a] === Instruction.read(b, variables) ? 1 : 0
  }

  static read (argument, variables) {
    if (Instruction.isVariable(argument)) {
      return variables[argument]
    } else {
      return parseInt(argument)
    }
  }

  static isVariable (input) {
    return ['w', 'x', 'y', 'z'].includes(input)
  }

  static parse (input) {
    return new Instruction(...input.split(' '))
  }
}

module.exports = { part1 }
