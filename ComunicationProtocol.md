# Command Client -> Server
In the following we will use:
1. !x! to indicate something that is required to user 
2. [x]+* to that x can be repeted 2 up to 4 times
  
## Registation process
R £00£ !username! £00£

Where:
* R stay for registation command
* username stay for username choose by user

## Unregitration process
U £00£ !username! £00£
  
Where:
* U stays for unregistration command
* username stays for username choose by user

## Setup process
S £00£ IDT1 £00£ IDT2 £00£ IDT3 £00£ IDPU1 £00£ IDPU2 £00£ IDPU3 £00£ IDPR [£00£ IDW £00£]+*

Where:
* S stays for setup command
* IDTn stays for ToolCard's id 
* IDPUn stays for PublicCard's id 
* IDPR stays for PrivateCrad's id
* IDW stays for WindowPattern's id

## Actions
### GetAmountToChange
A £00£ GATC £00£

### GetDraftedPoolIndex

A £00£ GDPI £00£

Where GDPI stays for draftpool index

### GetMatrixIndexFrom

A £00£ GMIF £00£

Where GMIF stays for index where dice is placed

### GetMatrixIndexTo

A £00£ GMIT £00£

Where GMIT stays for idex where dice has to be placed

### GetRoundTrackIndex

A £00£ GRTI £00£

Where GRTI stays
### UpdateDraftPool

A £00£ UDP £00£

### UpdateWindowPattern

A £00£ UWP £00£

### UpdateRoundTrack

A £00£ URT £00£

# Server -> Client 

## SendMessage

S £00£ message £00£

## chooseWindowPattern

CW £00£ ID1 £00£ ID2 £00£ ID3 £00£ ID4 £00£

## timerEnd

TE £00£ 

## timerStart

TS £00£

## sendScore 

SC £00£ *SC*

## sendGameStatus

SGS £00£ *GS*

# Objects serialization

In the following we propose a serialization of what we consider the three fundamental objects:

1. WindowPatternCard

 WP £00£ ID £00£ matrix.toString() £00£
 
2. GameStatus
  
  GS £00£ NPPLAY £00£ [{USERNMAE £00£ [WP] £00£ }]+* £00£ DF £00£ {dice...} £00£ TOL £00£ PUB £00£ PR
 
3.BoardRound 

  BR £00£ 
