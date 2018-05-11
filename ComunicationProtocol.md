# Command Client -> Server
In the following we will use:
1. <x> to indicate something that is required to user 
2. [x]+* to that x can be repeted 2 up to 4 times
  
## Registation process
R £00£ <username> £00£

Where:
* R stay for registation command
* username stay for username choose by user

## Unregitration process
U £00£ <username> £00£
  
Where:
* U stay for unregistration command
* username stay for username choose by user

## Setup process
S £00£ IDT1 £00£ IDT2 £00£ IDT3 £00£ IDPU1 £00£ IDPU2 £00£ IDPU3 £00£ IDPR [£00£ IDW £00£]+*

Where:
* S stay for setup command
* IDTn stay for ToolCard's id 
* IDPUn stay for PublicCard's id 
* IDPR stay for PrivateCrad's id
* IDW stay for WindowPattern's id

## Actions
### GetAmountToChange
A £00£ GATC
### GetDraftedPoolIndex
A £00£ GDPI
### GetMatrixIndexFrom
A £00£ GMIF
### GetMatrixIndexTo
A £00£ GMIT
### GetRoundTrackIndex
A £00£ GRTI
### UpdateDraftPool
A £00£ UDP
### UpdateWindowPattern
A £00£ UWP
### UpdateRoundTrack
A £00£ URT

