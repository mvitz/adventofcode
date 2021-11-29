const fixExpenseReport = (expenseReport) => {
  for (let i = 0; i < expenseReport.length; i++) {
    const firstEntry = expenseReport[i]
    for (let j = i + 1; j < expenseReport.length; j++) {
      const secondEntry = expenseReport[j]
      if (firstEntry + secondEntry === 2020) {
        return firstEntry * secondEntry;
      }
    }
  }

  throw new Error(`Unable to fix the given expense report: ${expenseReport}`)
}

module.exports = fixExpenseReport
