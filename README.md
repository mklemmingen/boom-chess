# boom-chess
Idiocracied chess, with health, guns, obstacles, boom and stuff

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Instructions for running in intelllij:
1. Click on Gradle on the right hand side bar
2. Gradle -> desktop -> Tasks -> others -> run

Game Assets in the folder /assets

core game-building:

/core/src/com.boomchess.game/BoomChess.java

   - create method for menuStart and gameCreation
   - render method for gameLoop

configuration for desktop-specific rendering:

/desktop/src/com.boomchess.game/DesktopLauncher.java

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Schematics for the Program

![Schematics for the Program. frontend on top, backend on bottom. the frontend is libGDX based and the backend is a 2D Array of a Soldier class, a Damage class, a Board Class and a couple of Classes for pieces. It is not a much-more-indepth diagram](https://github.com/mklemmingen/boom-chess/blob/master/readme_assets/Schematics_ProgrammierprojectHD.png?raw=true)


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

the front end framwork will be run my libGDX, which will help in things running smoothly and more easy to setup. 
Gradle will be used to easily work with libraries (dependencies) and for smooth game starts.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Board Size: 9x8

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Starting Menu Layout:

Boom-Chess

1. HELP!
   
4. Start a 2-player-game
5. Start a game against a bot

6. Options

7. Credits

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1.1 Learn the game

   ......  explanation about the different figurines, their stats, their dis/advantages 

5.1 Options about the game that will start
  4.2 Choose your queens name
  4.3 coin toss - winner decides board
  
6.1  Options about the game that will start
  6.2 Choose your queens name
  6.3 Choose the Difficulty of your enemy
  6.4 Choose the Layout of the board and obstacles
  

7.1 Options about the colours of the game-board, about colour-blindness

8.1 Let the credits and info about the game run down
   - display libGDX
   - display where code snippets have been taken from
   - display people that have worked on this project
   - display programs that have been used in creating the art and sounds

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Quick intro:
The game will be modernized using a different approach to chess. Each piece is a new piece resembling it only slightly in the way it can move. T
he King will be a General. The Towers will be tanks. The Pawns Infantry. The Runners will be Dogs. The Horses will be Helicopters. The Queen will be a Commando. 

The whole board will be 10x10. Each piece has a healthbar.  

The pieces have advantages and disadvantages for different kinds of enemies.  
At the end of each chess-like turn, the current players pieces will all attack anyone they can. 

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

quick ids for pieces:   // TODO UPDATE THE STATS TO THE CURRENT INGAME CHARACTERISTICS

General(King) - int: 10          / health: int: 50   / damage: 1-5  

   The most critical piece on the board. It can move one square in any direction (horizontally, vertically, or diagonally).
   if killed team loses
   takes only half the possible damage

Commando(Queen) - int: 20        / health: int: 50  / damage: 1-30  / advantages: +10 to attacking tanks 

   The most powerful piece. It can move horizontally, vertically, diagonally, and in any direction for any number of squares.
   on a randomized 1-5 scale, he takes (<random number>/5)*100 percent less damage
   
Tank(Rook) - int: 30             / health: int: 60  / damage: 10-20 / advantages: +5 to attacking infantry / disadavntages: deals -5 to wardogs

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

Infantry(Pawn) - int: 60        / health: int: 40  / damage: 01-20 / advantages: +5 to attacking helicopters / disadvantages: -5 to tank

   Pawns move forward one square but capture diagonally. On their first move, they have the option to move forward two squares. When a pawn reaches the opponent's back rank, it can be promoted to any other piece (typically a queen).
   the simple pawn
   Has a bonus on attacking helicopters. an easy target for war dogs.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

main-menu title:
Victory Sound by Lesiakower
https://pixabay.com/music/video-games-victory-screen-150573/

"Boom Sound" from "https://pixabay.com/sound-effects/vine-boom-162668/" at date: 11.10.23

Background-Music ingame:

"Retro Wave" from "https://pixabay.com/sound-effects/retro-wave-melodie-128-bpm-8970/" at date: 11.10.23

"epic-battle" by Lesiakower https://pixabay.com/music/video-games-epic-battle-153400/

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
general backend setup:

Soldier[][] gameBoard = new Soldier[9][8];

Tile will hold information as to if the tile has a :  - rock on it - a piece on it, which and which team, pieceID, Health 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

gameStage-Explanation:

the game Stage will be created by initialising the gameBoard. This gameBoard will hold the Soldiers.
The gameStage will create the gameBoard by looping trough it and creating the Overlay-GameBoard depending on what Soldiers are on what tile.
Each tile has a Listener on it that activated if the Widget is dragged - during dragging, its possible moves from its current Drag-Start location get calculated and 
overlayd above the gameBoard-Stage. It ends when a click occurs.

- game Loop Explanation //TODO 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Sources for Pictures:

the background has been created by Marty Lauterbach (mklemmingen) using LibreSprite
All Characters have been created by Marty Lauterbach (mklemmingen) using LibreSprite

Sources for tilesets:

https://schwarnhild.itch.io/basic-tileset-and-asset-pack-32x32-pixels  (Royalty free as of 11.10.23)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

Tutorial and Wiki Sources:

For Scene2D
https://www.youtube.com/watch?v=YbeDMmajH9s

For General libGDX and starter Project
https://libgdx.com/wiki/

For Usage of Tiled for tmx map and tileset conversion:
https://www.youtube.com/watch?v=N6xqCwblyiw

-----------
source skins for Scene2DUI
https://github.com/czyzby/gdx-skins//master/flat
https://github.com/czyzby/gdx-skins/tree/master/commodore64

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Documentation:

Creation of the Background in LibreSprite https://github.com/LibreSprite/LibreSprite

![Background Creation](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/backgroundCreation.png?raw=true)

creating better PixelArt Icons for the Soldier Pieces using bings ai creation powered by Dall-E 3

![Icon Creation using DallE3](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/creatingBetterPixelArtIconsWithDallE3.png?raw=true)

Creation of mixed Icons for making the Pieces more diverse in their type USING inkscape https://inkscape.org

![Mixed Icons Creation in inkscape](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/combiningAndRefining.png?raw=true)

GameBoard Debugging in IntelliJ IDE Community Edition

![GameBoard Debugging in IntelliJ IDE Community Edition](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/DebugginTheGameBoard.png?raw=true)

Creation of the Boom Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite

![Creation of the Boom Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/How_to_Boom-Logo.png?raw=true)

Creation of the Move Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite

![Creation of the Move Logo pixel by pixel in LibreSprite https://github.com/LibreSprite/LibreSprite](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/Move_Logo_Creation.png?raw=true)

Creation of the Schematics USING inkscape https://inkscape.org

![Schematics Creation in inkscape](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/schematicsCreation.png?raw=true)

Creation of the underlaying .tmx tiled map using TILED https://www.mapeditor.org/

![Creation of the underlaying .tmx tiled map using TILED](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/TiledUsage.png?raw=true)

Using Inkscape for refining Icons

![Using Inkscape for refining Icons](https://github.com/mklemmingen/The-Boom-Chess/blob/master/readme_assets/UsingInkScapeForRefininigPNGs.png?raw=true)

