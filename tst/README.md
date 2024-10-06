# tst

## What Is This? 
This is my solution to the TST Programming Exercise(s).

It is written by a developer who has last seen Scala over 2 years ago it was version 2.12.
I did my best to adjust what I knew to work in Scala 3.4 and compile without any warnings about deprecated code. 
Mission accomplished! 

This solution is written in an imperative rather than functional style, 
and it can definitely be improved at the cost of code complexity, but I chose not to go that route. 
This is a "naive, intuitive" version that still allowed me to have some fun with Scala collections.

Feel free to contact me if you want to discuss this code or if you just want to chat.


## How Do We Run Or Test It?

Good question. I don't have a quick-and-easy way to setup any front end, so you'd have to go old-school at this time.

### Input

Look at the test directory. `CruiseWizardTest` and `PromoFinderTest` have sections with some hard-coded input and expected output.  
It should be pretty obvious how to replace the existing input and output.

### Running Tests
`sbt test` in your terminal will do the trick, an IDE might also work (I used IntelliJ).
