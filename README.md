# boom-chess
Idiocracied chess, with health, guns, obstacles, boom and stuff

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Instructions for running in intelllij:
1. Click on Gradle on the right hand side bar
2. Gradle -> desktop -> Tasks -> others -> run

Game Assets in the folder /assets

core game-building:

/core/src/com.boomchess.game/BoomChess.java

configuration for render:

/desktop/src/com.boomchess.game/DesktopLauncher.java

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A daring big 10x10 board

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Decide how many obstacles you want in your board - how about a rock? how about 5?

In a 2-Player-Game, a coin throw decides who decides! 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Starting Menu Layout:

Welcome to Boom-Chess
Do you dare to blow up?

1. HELP!
2. Read the lore
   
4. Start a 2-player-game
5. Start a game against a bot

6. Options

7. Credit where Credit is due

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1.1 Learn the game

   ......  explanation about the different figurines, their stats, their dis/advantages 

2.1 

  ....... Big Lore about the game world you find yourself in

4.1 Options about the game that will start
  4.2 Choose your queens name
  4.3 coin toss - winner decides board
  
5.1  Options about the game that will start
  5.2 Choose your queens name
  5.3 Choose the Difficulty of your enemy
  5.4 Choose the Layout of the board and obstacles
  

6.1 Options about the colours of the game-board, about colour-blindness

7.1 Let the credits and info about the game run down
   - display libGDX
   - display where code snippets have been taken from
   - display people that have worked on this project
   - display programs that have been used in creating the art and sounds


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

the front end framwork will be run my libGDX, which will help in things running smoothly and more easy to setup. 
Gradle will be used to easily work with libraries (dependencies) and for smooth game starts.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Quick intro:
The game will be modernized using a different approach to chess. Each piece is a new piece resembling it only in the way it can move. The King will be a General. The Towers will be tanks. The Pawns Infantry. The Runners will be Dogs. The Horses will be Helicopters. The Queen will be a Commando. 
There will also be an extra Piece called Artillery that can attack at a distance of 3 tiles and move sideways 2 tiles and diagonally 1 tile per round. 
The whole board will be 10x10. Each piece has a healthbar. Each piece will attack everything it can attack, whose target, except for artillery, must be within the first next tiles. If multiple tiles around have enemies, the D20 damage will be split between enemies. 
The pieces have advantages and disadvantages for different kinds of enemies.  
At the end of each chess-like turn, the current players pieces will all attack anyone they can. 
at each turn, a D20 is thrown to determine damage on each other

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

quick ids for pieces:

General(King) - int: 10          / health: int: 50   / damage: 5-20  

   The most critical piece on the board. It can move one square in any direction (horizontally, vertically, or diagonally).
   if killed team loses
   neither resistant or easily killed.

Commando(Queen) - int: 20        / health: int: 50  / damage: 1-30  / advantages: +10 to attacking tanks 

   The most powerful piece. It can move horizontally, vertically, diagonally, and in any direction for any number of squares.
   she is your secret weapon. Name her yourself when you start the game.
   Make her be "Private Princess", "Big Betty", "Power Ranger" or more. She will crush enemies, but is more easily killed if surrounded on 2 or more sides.

Tank(Rook) - int: 30             / health: int: 60  / damage: 10-20 / advantages: +5 to attacking infantry

   These are often represented as towers. They can move horizontally or vertically for any number of squares.
   high health. hard on other armour.
   easily killed by helicopters. acts like towers

War Dogs(Bishop) - int: 40      / health: int: 40   / damage: 5-20  / advantages: +5 to attacking infantry 

   They move diagonally for any number of squares.
   is the end of faith for all infantry. easy target for helicopters
   the war dogs fear them cause they go broom. 
 
Helicopter(Knight) - int: 50    / health: int : 50  / damage: 10-20 / advantages: +5 to attacking tanks

   Knights move in an L-shape: two squares in one direction (either horizontally or vertically) and then one square in a perpendicular direction. Knights can jump over other pieces.
   High movement. doesnt care about obstacles.
   high effectiveness against armour. 

Infantry(Pawn) - int: 60        / health: int: 40  / damage: 01-20 / advantages: +5 to attacking helicopters 

   Pawns move forward one square but capture diagonally. On their first move, they have the option to move forward two squares. When a pawn reaches the opponent's back rank, it can be promoted to any other piece (typically a queen).
   the simple pawn
   Has a bonus on attacking helicopters. an easy target for war dogs.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

main-menu title:
epic-battle by Lesiakower
https://pixabay.com/music/video-games-epic-battle-153400/

"Boom Sound" from "https://pixabay.com/sound-effects/vine-boom-162668/" at date: 11.10.23

Background-Music ingame:
   "Retro Wave" from "https://pixabay.com/sound-effects/retro-wave-melodie-128-bpm-8970/" at date: 11.10.23

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
general backend setup:

Tile[][] board = new Tile[10][10];


Tile will hold information as to if the tile has a :  - rock on it - a piece on it, which and which team 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Sources for Pictures:

the background has been created by Marty Lauterbach (mklemmingen) using LibreSprite
All Characters have been created by Marty Lauterbach (mklemmingen) using LibreSprite

Sources for tilesets:

https://schwarnhild.itch.io/basic-tileset-and-asset-pack-32x32-pixels  (Royalty free as of 11.10.23)
