# Command Client -> Server

In the following we will use:
1. !x! to indicate something that is required to user. 
2. [x]+* to that x can be repeted 2 up to 4 times.
3. £00£ is a delimiter we use to separate strings.
  
## Registation process

R £00£ !username! £00£

Where:
* R denotes registation command.
* username denotes username choose by user.

## Unregitration process

U £00£ !username! £00£
  
Where:
* U denotes unregistration command.
* username denotes username choose by user.

## Setup process

S £00£ IDT1 £00£ IDT2 £00£ IDT3 £00£ IDPU1 £00£ IDPU2 £00£ IDPU3 £00£ IDPR [£00£ IDW £00£]+*

Where:
* S denotes for setup command.
* IDTn denotes ToolCard's id.
* IDPUn denotes PublicCard's id. 
* IDPR denotes PrivateCrad's id.
* IDW denotes WindowPattern's id.

## Actions

With actions we denotes the set of all command we have identified as essential to play this game.

### GetAmountToChange

A £00£ GATC £00£
 
Where GAT denotes the value to assign to a dice.
 
### GetDraftedPoolIndex

A £00£ GDPI £00£

Where GDPI denotes draftpool index.

### GetMatrixIndexFrom

A £00£ GMIF £00£

Where GMIF denotes matrix index where dice is placed.

### GetMatrixIndexTo

A £00£ GMIT £00£

Where GMIT denotes matrix idex where dice has to be placed.

### GetRoundTrackIndex

A £00£ GRTI £00£

Where GRTI denotes the index of round track from which take a dice.


# Server -> Client 

## SendMessage

S £00£ message £00£

Where message is a message server send to client.

## chooseWindowPattern

CW £00£ ID1 £00£ ID2 £00£ ID3 £00£ ID4 £00£

Where IDn denotes idex of windowpatterncard.

## timerEnd

TE £00£ 

## timerStart

TS £00£

## sendScore 

SC £00£ *SC*

Where *SC* is a Score object in string format.

## sendGameStatus

SGS £00£ *GS*

Where *GS* is a GameStatus object in a string format.

# Objects serialization

In order to send objects through a socket we decided to override the method toString(); doing that we create a string which rappresent the specific object whose toString() is applied.
