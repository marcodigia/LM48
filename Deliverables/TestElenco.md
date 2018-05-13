# Test
## DiceBagTest
- [x] See if DiceBag constructor creates two different set of dices 
- [x] Take 0 dices
- [x] Take all dices
- [x] Put a dice on DiceBag

## DiceTest
- [x] Reroll the dice
- [x] See if two dice are equals

## DraftPoolTest
- [x] Extract n dices from draft pool
- [x] Reroll dices of draft pool
- [x] Remove dice from draft pool
- [x] Add dice to draft pool

## BoardRoundTest
- [x] upDate score of players who belongs to board round 
- [x] Test if different player score is correctly updated
- [x] Test if correctly returns the winners in case the winner is only one
- [x] Test if correctly returns the winners in case there is more then one winner
- [x] Check if returns the the scoreboard right if is empty
- [x] Check if returns the the scoreboard right if is not empty

## MatrixTest
- [x] Place dices on matrix
- [x] Remove dices on matrix
- [x] Get all dices on matrix
- [x] Move dices on matrix
- [x] Try to place dices on matrix
- [x] Get a row of matrix

## TakeDiceBasic
- [x] Check if correctly place a dice from the draftpool to the windowpattern with no restriciton
- [ ] Check if correctly removes the dice from the draftpool
- [ ] Check if correctly place a dice from the draftpool to the windowpattern with restriciton

## UseToolCardBasic
- [x] Check if correctly execute the Action

## ChangeDiceValueByOne
- [x] Check if correctly change dice value if is the value is in bounds
- [x] Check if do not change if the dice value is at the bounds

## MoveOneDiceIgnoringValueTest
- [x] Check if correctly move a dice if no restriction must be respected
- [x] Check if correctly move a dice if there is a value restriction to be respect
- [ ] Check if fail to move a dice if Color restriction must be respected
- [ ] Check if fail to move a dice if Adjacency restriction must be respected

## MoveOneDiceIgnoringColorTest
- [x] Check if correctly move a dice if no restriction must be respected
- [x] Check if correctly move a dice if there is a color restriction to be respect
- [ ] Check if fail to move a dice if Value restriction must be respected
- [ ] Check if fail to move a dice if Adjacency restriction must be respected

## MoveTwoDice
- [x] Move two dice in a legal position
- [x] Move same dice in two different position
- [x] Move two dice in same position
- [x] Move one dice in a legal position and the other in an illegal position

## RerollDice
- [x] Reroll a dice and place it in a legal position
- [x] Reroll a dice and place it in an illegal position because of a Color restriction
- [x] Reroll a dice and place it in an illegal position because of a Value restriction

## Player
- [x] Check if correctly use Basic Action TakeDice
- [x] Check if correctly use Basic Action UseToolCard
