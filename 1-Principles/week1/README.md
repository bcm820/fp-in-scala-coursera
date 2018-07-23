## Programming Paradigms

### Imperative programming

- Uses sequences of statements to determine how to reach a certain goal.
- Modifies mmutable variables using assignments, composed with control structures like if-else, for, break, continue, etc.
- Problem: Scaling up. Imperative programming conceptualizes programs word by word. But we want to reason in larger structures. We need higher level abstractions (i.e. theories) of collections, polynomials, geometric shapes, strings, documents, etc.
- In math, theories do not allow mutation of values, since there are fixed laws determing the relationship between the values and operators. Mutations break such laws.

### Functional programming:

- Defines and uses theories (i.e. functions) to reason about the structure of a program.
- In a restricted sense, uses mutable variables without assignments, loops, and other imperative control structures.
- Functions are first class citizens. Whatever you can do with data, you can do with functions. i.e. Define them, pass them into parameters, return them from functions, use them with operators, etc.
- Benefits:
  - Simpler reasoning principles
  - Better modularity
  - Exploiting parallelism for multicore and cloud computing. See [this video](https://www.youtube.com/watch?v=3jg1AheF4n0).

## Elements of Programming

- Every language provides 1) primitive expressions, 2) ways to combine expressions, 3) ways to abstract expressions and reference by name
- Access Scala's `Read-Eval-Print-Loop (REPL)` via `sbt console`.
