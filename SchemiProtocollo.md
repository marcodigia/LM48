   
                                CLIENT         GameStart              SERVER
                                   +                                      +
                                   |                                      |
                                   |                                      |
                                   |            Username                  |               WaitingRoom
                                   +-------------------------------------->                    +
                                   |                                      |                    v
                                   |                                      |               StartTimeOut()
                                   |                                      |                    +
                                   |                                      |                    v
                                   |                                      |               NewGameStart()
                                   |                                      |                    +
                                   |                                      |                    v
                                   |                                      |               GameSetUp()
                                   |                                      |                    +
                                   |          Id1,Id2,Id3,Id4             |                    v
                                   <--------------------------------------+  ServerRete.serverAskClientForWindow()
                                   |                                      |                    +
            Client.UI.chooseWP()   |                Id                    |                    v
                                   +-------------------------------------->     ServerRete.setWindowPattern()
                                   |                                      |                    +
                                   |             GameContext              |                    v
             Client.UI.update()    <--------------------------------------+        ServerRete.setGameContext()
                                   |                                      |
                                   |                                      |
                                   +                                      |
                                                                          +


                    CLIENT                                  SERVER
                       +             RoundStart               +
       UI.Disactivated |                                      |     ServerRete.NextRound()
                       |                                      |              +
                       |                                      |              v
                       |                                      |      ChangePlayerStatus
                       |                                      |              +
                       |              TimerStart              |              v
        UI.Activeted   <--------------------------------------+        VirtualView
              +        |                                      |
              v        |                                      |
        UseToolCard    |                                      |
              +        |                                      |
              v        |                                      |
          PlaceDices   |                                      |
              +        |                                      |
              v        |                 Action               |
          UseAction    +-------------------------------------->          VirtualView
                       |                                      |              +
                       |                                      |              v
                       |                                      |       Player.doAction()
                       |                                      |              +
                       |                                      |              v
                       |                                      |          GameContext
                       |                                      |              +
                       |                GameStatus            |              v
                       <--------------------------------------+         VirtualView
                       |                                      |
                       |                                      |
                       |                                      |
                       +                                      +



                     CLIENT           EndGame              SERVER
                       +                                      +
                       |                                      |       Game
                       |                                      |         +
                       |                                      |         v
                       |                                      |    EndOfGame()
                       |                                      |         +
                       |                                      |         v
                       |                                      |  GenerateScore()
                       |                                      |         +
                       |                                      |         v
         UI.GameScene  <--------------------------------------+   VirtualView()
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       |                                      |
                       +                                      +
