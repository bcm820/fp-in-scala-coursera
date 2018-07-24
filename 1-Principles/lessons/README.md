# Programming Paradigms

## 1. Imperative programming

- Uses sequences of statements to determine how to reach a certain goal.

- Modifies mmutable variables using assignments, composed with control structures like if-else, for, break, continue, etc.

- Problem: Scaling up. Imperative programming conceptualizes programs word by word. But we want to reason in larger structures. We need higher level abstractions (i.e. theories) of collections, polynomials, geometric shapes, strings, documents, etc.

- In math, theories do not allow mutation of values, since there are fixed laws determing the relationship between the values and operators. Mutations break such laws.

## 2. Functional programming

- Defines and uses theories (i.e. functions) to reason about the structure of a program.

- In a restricted sense, uses mutable variables without assignments, loops, and other imperative control structures.

- Functions are first class citizens. Whatever you can do with data, you can do with functions. i.e. Define them, pass them into parameters, return them from functions, use them with operators, etc.

- Benefits:
  - Simpler reasoning principles.
  - Better modularity.
  - Exploiting parallelism for multicore and cloud computing. See [this video](https://www.youtube.com/watch?v=3jg1AheF4n0).

<br>

# Function Evaluation Strategies

Every language provides 1) primitive expressions, 2) ways to combine expressions, 3) ways to abstract expressions and reference by name.

A non-primitive expression (i.e. an expression with operators that must be reduced) is evaluated from left to right by applying operators to operands (as with algebra):

```
(2 * pi) * radius:
(2 * 3.14159) * radius:
6.28318 * radius:
6.28318 * 10:
62.8318
```

## 1. Call-by-value (substitution model)

Reduces all expressions passed into a function into values before applying the function with the values as arguments:

```
sumOfSquares(3, 2+2):
sumOfSquares(3, 4):
square(3) + square(4):
3 * 3 + square(4):
9 + square(4):
9 + 4 * 4:
9 + 16:
25
```

Its advantage is that it evaluates every function argument only once.

By default, Scala (as well as most other languages) use the call-by-value evaluation strategy, since in most cases it is more optimal because it only evaluates function arguments once.

## 2. Call-by-name

Applies the function to unreduced arguments before rewriting the function application:

```
sumOfSquares(3, 2+2):
square(3) + square(2+2):
3 * 3 + square(2+2):
9 + square(2+2):
9 + (2+2) * (2+2):
9 + 4 * (2+2):
9 + 4 * 4:
25
```

Its advantage is that not all arguments are evaluated if its parameter is unused in the function application.

To use the call-by-name strategy in Scala, use `=>` within a parameter's definition: `x: => Int`

### Example #1: Encode `&&` as a function without using the actual operands:

```
def and(x:Boolean, y: => Boolean) = if(x) y else false
def and(x:Boolean, y: => Boolean) = if(!x) false else y
```

Call-by-name is used for `y`. It is only evaluated if `x` evaluates to true.

If `x`, return `y` which may be true or false. If not `x`, return false right away.

### Example #2: Do the same with `||`:

```
def or(x:Boolean, y: => Boolean) = if(x) true else y
def or(x:Boolean, y: => Boolean) = if(!x) y else true
```

Similar to the example above, `y` is only evaluated if `x` evaluates to false.
If `x`, return true right away. If `!x`, return `y` which may be true or false.

_Note: Scala uses `if-else` with expressions instead of statements._

<br>

# Value Definitions

Just as function parameters can be passed by value or name, so can value definitions. Consider these two definitions:

```
def x = square(y)
val z = square(y)
```

`def` evaluates definitions via call-by-name. They are evaluated and applied on each use. In the example, `x` will always re-evaluate `square(y)` when referenced.

`val` evaluates definitions via call-by-value. They are evaluated at the point of definition. In the example, `z` will always refer to its initial evaluation (i.e. some `Int`).
