README- Circ


-Header: 
-“Circ” by Vinh Doan and Yuta Arai
-5/27/14


-Description:
-“Circ” is a multiplayer game, in which each player is a circular ball.
* The game is in a top down 2-D view  with a grid for placing objects.
* Each player/ball can move freely in 4 directions regardless of the grid, and slowly accelerate the longer they move.
* There are walls that can impede the movement of the ball.
* There are also different power ups players can “land on”/pass through which give temporary abilities such as invincibility, boost pads, bombs, and juke juice (increases control of the ball).
* The game is for mainly for multiplayer, but also has computer AI that players can specify the levels. The level of the computers are from 1 through 10 with 1 being the most random and 10 being the smartest.
* Using these, “Circ” contains several different games with different rules which put players against each other
* Games:
   * Last Man Standing:
      * Each time specified round, a set number of the players are marked. They can collide with other non-marked players to pass on their “mark”. At the end of each round, those still marked lose and die.
      * The rounds continue until there is one person left who wins!
   * Sharks and Minnows
      * In this game, the stage has two ends, red and blue.
      * At the beginning, one player is marked as the “shark” and cannot go into the ends.
      * On the first round, non-shark players try to make it from red to blue.
      * If the shark touches one of these players they die and become a shark the next round along with the others.
      * After time runs out, those who made it to blue stay alive, and the next round begins
      * The next round, remaining non-sharks go from B to A, as sharks try to tag them.
-Instructions:
* There can be up to 1-4 players.
* Balls that are not controlled by a player will be a controlled by an AI.
* The players can control the movement of their ball through the arrows keys, WASD keys, IJKL keys, and TFGH keys.
* Players follow rules of above games and try to win


-Feature list:
* Must-have
   * Stage -The game will have different stages that will contain walls, obstacles, spikes, and power ups.
   * Balls - Represents the balls that the player can control. It can move in any direction, but slowly accelerates towards the direction of the key that the player presses. Balls can collide with each other, the walls, and the obstacles to change directions. The balls will have two states: marked and non-marked states. When the a marked ball collides with a non-marked balls, their states will be switched. When a ball dies, it will turn into a ghost ball in which it can follow along the game but cannot interact with others.
   * Time - Keeps track of the time
   * Walls - Represents the surrounding area of the game that the ball cannot pass through. When a ball hits a wall, it will divert it’s direction.
   * Multiplayer - At the very least, multiplayer with a large screen
* Want-to-have
   * Powerups - The balls can “land on”/pass through which give temporary abilities such as invincibility, speed boosts, or killing power
   * Split-Screen Multiplayer - The screen will be split into the amount of players in the game with each screen focused onto one player. Each player will have a different set of keys to control their players.
* Stretch
   * Online Multiplayer - Multiplayer ONLINE! Each player works using their own screen
   * More game modes - In addition to the listed game mode, possibly more competition between the players.
   * Single Player game mode - AI against the player
-Class list:
* Ball - Represents the player, can move in directions, accelerating with keypress
* BallAI - Represents the AI that controls the balls that are not controlled by players
* Stages - Represents the class that can create a stage from a text file        
* Wall - Represents a rectangular wall that ball cannot move past
* Powerups - Represents PowerUps
   * StillPowerUp - Represents PowerUp that doesn't spawn
      * Bomb - Represents a rectangular powerup that shoots the ball off the opposite direction when touched
      * BoostPad - Represents a rectangular powerup that increases the ball’s speed in the direction that it is moving in
   * Immunity - Represents a rectangular powerup that makes the ball temporarily immune from being marked
   * JukeJuice - Represents a rectangular powerup that allows the movement of the ball to be more crisp
* MovingImage -Represents a rectangular game object image that can be moved
* GameManager - Represents score and time
* GameUI - Represents additional UI inGame
* GamePanel - Represents panel for game
* MiniGamePanel - Represents the panel for games during splitscreen
* GameMode - Represents an abstract class for the different game modes
   * LastManMode - Represents the “Last Man Standing” mode
   * SharksMode - Represents the Sharks and Minnows game mode
* ModePanel - Represents the different modes that the game can have
* MiniModePanel - Represents the small mode panels within ModePanel
* PlayerPanel - Represents the starting menu panel
* MenuPanel - Represents opening start up menu Page
* Circ - Represents the main class
-Responsibility list:
Vinh: Game Modes, Game Panel, StageReader, Menu, SplitScreen, Game UI
Yuta: Ball, Powerup, Level Design/Maps,BallAI