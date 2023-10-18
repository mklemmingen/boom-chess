package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;


public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters
	private SpriteBatch batch;
	private OrthographicCamera camera;
	// loading of essential background images
	private Texture background;
	// start of asset loading Sound and Music
	public Sound boom;
	public static Music background_music;
	public static Music menu_music;
	// usage for Scene2DUI-skins and stages
	private static Skin skin;
	private static Skin progressBarSkin;
	private static Stage currentStage;

	// for the tiled map
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	// for determining game stage affairs down the line
	public static String winnerTeamColour;

	// for determining which tiles should be slightly highlighted with a red hue to indicate that the dragged
	// piece can be dropped there
	// public static ArrayList<Coordinates> validMoveTiles;

	// for setting the current "Mover" of the game
	public static boolean isRedTurn = true;

	public static boolean legitTurn = false;

	public static boolean inGame = false;

	// for the x-marker overlay over the game-field
	public static boolean renderOverlay = false;
	public static Stage possibleMoveOverlay;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.png");

		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// creation of the camera fitting to the set resolution in DesktopLauncher

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1536, 880);


		// load the boom sound effect and background music
		boom = Gdx.audio.newSound(Gdx.files.internal("sounds/boom.ogg"));

		background_music = Gdx.audio.newMusic(Gdx.files.internal("music/retro-wave.wav"));

		// load the menu music

		menu_music = Gdx.audio.newMusic(Gdx.files.internal("music/victory-screen.mp3"));

		// for the tiled map used as the chess board

		tiledMap = new TmxMapLoader().load("map/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		/*
		 * creation of the stages for the menu - this allows the Scene2D.ui to be used for quick swapping of screens
		 * and the usage of the buttons/ui-elements/so-called actors and child actors to be used
		 * stages will be the way we display all menus and the game itself
		 */

		// skin (look) of the buttons via a prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));
		// skin (look) of the progress bar via a prearranged json file
		progressBarSkin = new Skin(Gdx.files.internal("progressBarSkin/neon-ui.json"));

		// create the big game Board as a object of the Board class
		Board.initialise();

		currentStage = createMainMenuStage();

	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.draw(background, 0, 0);
		batch.end();

		// for the tiled map
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// for the overlay of possible moves
		if (renderOverlay){
			possibleMoveOverlay.act();
			possibleMoveOverlay.draw();
		}

		// for the stages, displays only stage assigned as currentStage, see method switchToStage
		currentStage.act();
		currentStage.draw();


		// Image of the currentMover
		Table currentMover = new Table();
		currentMover.setSize(250, 125);
		currentMover.setPosition(currentMover.getWidth()/6,
				currentMover.getHeight()/2);


		// for the in-game loop for setting the turn
		if (isRedTurn && inGame) {
			// red team's turn
				// draw Stage with Red Logo
				Image redMove = new Image(new Texture(Gdx.files.internal("red_Move.png")));
				currentMover.add(redMove);
			// if red team move was legit (legitTurn true)
			if (legitTurn) {
				// update position draw new gameBoard inside the update function
				// drawTheGameBoard(gameBoard);

				// calculate damage from all red pieces to all green pieces
				// TODO link existing functions

				// set variable of isRedTurn to false after all damage dealt
				isRedTurn = false;
				// set variable of legitTurn to false
				legitTurn = false;
				}
		} else {
			// Green team's turn
			// draw Stage with Green logo
			Image greenMove = new Image(new Texture(Gdx.files.internal("green_Move.png")));
			currentMover.add(greenMove);
			// if green team move was legit
			if (legitTurn) {
				// update position draw new gameBoard inside the update function
				// drawTheGameBoard(gameBoard);
				// calculate damage from all green pieces to all red pieces
				// TODO link existing functions

				// set variable of isRedTurn to true after all damage dealt
				isRedTurn = true;
				// set variable of legitTurn to false
				legitTurn = false;
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
		possibleMoveOverlay.dispose();
	}

	private static void switchToStage(Stage newStage) {
		// this method removes the currentStage and loads a new one
		currentStage.dispose();
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	private static void addToStage(Table root) {
		// this method adds a new stage to the currentStage
		currentStage.addActor(root);
	}

	private static Stage createMainMenuStage() {

		// setOverlay to false
		renderOverlay = false;

		Stage menuStage = new Stage();
		Gdx.input.setInputProcessor(menuStage);

		// start menu music
		background_music.stop();
		menu_music.setLooping(true);
		menu_music.play();

		// Begin of Main Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		menuStage.addActor(root);

		final Image title = new Image(new Texture("BoomLogo.png"));
		root.add(title).top().padBottom(20);
		root.row();

		TextButton helpButton = new TextButton("Help!", skin);
		root.add(helpButton).padBottom(20);
		// if help button is pressed, create a new stage for the help information
		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createHelpStage());
			}
		});
		root.row();

		TextButton play2Button = new TextButton("Play a 2 Player Game", skin);
		root.add(play2Button).padBottom(20);
		play2Button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createGameStage(false));
			}
		});
		root.row();

		TextButton playBotButton = new TextButton("Play a Game Against the Computer", skin);
		root.add(playBotButton).padBottom(20);
		playBotButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createGameStage(true));
			}
		});
		root.row();

		TextButton optionsButton = new TextButton("Options", skin);
		root.add(optionsButton).padBottom(20);
		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createOptionsStage());
			}
		});
		root.row();

		TextButton creditsButton = new TextButton("Credits", skin);
		root.add(creditsButton).padBottom(20);
		creditsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createCreditsStage());
			}
		});
		root.row();

		TextButton exitButton = new TextButton("Exit", skin);
		root.add(exitButton).padBottom(20);
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// for exiting the game
				Gdx.app.exit();
				// for ending all background activity on Windows systems specifically
				System.exit(0);
			}
		});
		root.row();

		// End of first menu-layer Layout
		return menuStage;
	}

	private static Stage createHelpStage() {
		Stage helpStage = new Stage();

		// Begin of Help Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		helpStage.addActor(root);

		// TODO: here starts a long string of text that will be displayed in the help menu about
		//		 what the premise of the game is and what to expect. also a quick overview about the controls and
		//		 pieces (their stats)

		// back button to return to the main menu
		TextButton backButton = new TextButton("Back", skin);
		root.add(backButton).padBottom(20);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createMainMenuStage());
			}
		});
		root.row();

		return helpStage;
	}

	private static Stage createOptionsStage() {
		Stage optionsStage = new Stage();

		// Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		optionsStage.addActor(root);

		// TODO add buttons that change the volume of the music and sound effects as well as
		//  yet to be decided variables in the backend like NIKI Difficulty etc

		// back button to return to the main menu
		TextButton backButton = new TextButton("Back", skin);
		root.add(backButton).padBottom(20);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createMainMenuStage());
			}
		});
		root.row();

		return optionsStage;
	}

	private static Stage createCreditsStage() {
		Stage creditsStage = new Stage();

		// Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		creditsStage.addActor(root);

		// TODO add long list of names of people who worked on the game, what they did, sources and tools used

		// https://javadoc.io/doc/com.badlogicgames.gdx/gdx/latest/com/badlogic/gdx/scenes/scene2d/ui/TextArea.html
		// so called TextArea Widget used for displaying text in a scrollable box

		// back button to return to the main menu
		TextButton backButton = new TextButton("Back", skin);
		root.add(backButton).padBottom(20);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createMainMenuStage());
			}
		});
		root.row();

		return creditsStage;
	}

	private static Stage createGameStage(boolean isBotMatch) {

		inGame = true;

		Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// xMarkerOverlay
		possibleMoveOverlay = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// stop menu music and start background_music
		menu_music.stop();
		background_music.setLooping(true);
		background_music.play();

		// CHECKED try to implement the game board as a tiled map and the pieces as actors on top of it
		//  combine the tiled map renderer with the stage renderer? Research: addressing individual .tmx tiles in code
		//  - corresponding to the 2D Array Game Board, the pieces on it, their stats as clean health bars.
		//  ----------------------------------------------------------------------------------------------
		//  Actor-Images must be 80x80px. Add Exit-Button in the Bottom right corner of the screen
		//  Actors should be able to be drag-droppable and snap to the grid. They can only move to tiles
		//  their chess characteristics allow them to. This should be checked by the backend, and be send back as tile
		//  coordinates, so the allowed tiles can temporarily be highlighted. If piece is dropped on an allowed tile,
		//  update 2D Array with this new information. End turn.
		//  Calculate Damage from all the current Players pieces onto enemy pieces. All hit pieces should be highlighted.
		//  If a piece is killed, remove it from the board and the 2D Array. If a piece is killed, check if that piece
		//  was the general and end the game by saving who won (who killed the other general).
		//  If not, switch to the next player.
		//  If yes, create Game-End-Stage: display the winner and a button to return to the main menu.

		// TODO while this function holds the gameStage and updates the tiled map, a different function
		//  for input-handling is called on for the red team depending if isBotMatch is true or false. Meaning if its
		//  a 2-Player game or a one player game against a bot. If false, the input-handling is indiscriminate and just
		//  run in a while both-general-alive loop until the game is over, switching between
		//  green team movable followed by damage calculation and red team movable fol. by red team damage calculation.

		// TODO OUTLINE of game handling:
		//  while both generals are alive do:
		//  	green moves calculate
		//  	damage green onto red
		// 		save damage-info as a list of tuples (attacker, defender, damage) for immediate display as dotted red line
		//  	if a general is killed, end game and display winner
		//  	if isBotMatch == false
		//  		red moves calculate
		//  	else
		//  		redBot moves calculate
		//  	damage red onto green
		//  	save damage-info as a list of tuples (attacker, defender, damage) while iterating for immediate
		//  	display as dotted red line
		//  	if a general is killed, end game and display winner

		// render the gameBoard by iterating through the 2D Array Soldiers[][] gameBoard
		// checking which type of piece at position and drawing the correct image there

		// create the first gameBoard
		setGameBoard();

		// add game board
		gameStage.addActor(drawTheGameBoard());

		// create another stage for the back to main menu button
		Table backTable = new Table();
		backTable.setSize(400, 80); // determines the frame size for the backTable (button: to main menu)
		// bottom right the table in the parent container
		backTable.setPosition(Gdx.graphics.getWidth() - backTable.getWidth(), 0);
		gameStage.addActor(backTable); // Add the table to the stage

		// Exit to Main Menu button to return to the main menu
		TextButton menuButton = new TextButton("Return to Main Menu", skin);
		menuButton.align(Align.bottomRight);
		backTable.add(menuButton);
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createMainMenuStage());
				// create a new gameBoard since game has ended
				setGameBoard();
			}
		});

		return gameStage;
	}

	private static void setGameBoard() {
		Board.initialise();
	}

	private static Table drawTheGameBoard() {

		Soldier[][] gameBoard = Board.getGameBoard();

		// Begin of GameLayout - Root Table arranges content automatically and adaptively as ui-structure
		Table root = new Table();

		root.setSize(720, 640);
		root.center(); // Center the gameBoard in the parent container (stage)
		// refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
		root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
				(Gdx.graphics.getHeight() - root.getHeight()) / 2f);

		// root.setTouchable(Touchable.enabled);

		// for the size of the tiles
		int tileSize = 80;
		int numRows = 8;
		int numColumns = 9;

		for (int i = 0; i < numRows; i++) {
			// add a new stage Table row after each row of the gameBoard
			root.row();
			for (int j = 0; j < numColumns; j++) {
				// create a new box like widget at each position of the board and add it to the root table
				// it is 80x80 pixels, holds the image of the piece at that position and is movable to other positions
				// switch statement to check which type of piece it is

				// current health value of the coordinate in the 2D Array IF it is taken, otherwise make it -1
				int currentHealth = -1;
				// if the position is taken
				if (gameBoard[j][i].getTaken()) {
					// get the current health value of the piece at that position
					currentHealth = gameBoard[j][i].getHealth();
				}
				switch (gameBoard[j][i].getSoldierType()) {
					case "general":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("general_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("general_green_right.png", currentHealth, j, i
							)).size(tileSize);
						}
						break;
					case "infantry":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("infantry_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("infantry_green_right.png", currentHealth, j, i
							)).size(tileSize);
						}
						break;
					case "helicopter":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_green_right.png", currentHealth, j, i
							)).size(tileSize);
						}
						break;
					case "tank":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("tank_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("tank_green_right.png", currentHealth, j, i
							)).size(tileSize);
						}
						break;
					case "commando":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("commando_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("commando_green_right.png", currentHealth,j, i
							)).size(tileSize);
						}
						break;
					case "wardog":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("war_dog_red_left.png", currentHealth, j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("war_dog_green_right.png", currentHealth, j, i
							)).size(tileSize);
						}
						break;
					case "empty":
						// Empty box (no image)
						root.add(drawPiece("empty.png", currentHealth, j, i)).size(tileSize);
						break;
				}
			}
		}
		return root;
	}

	private static Actor drawPiece(final String fileLocation, final int health,
								   final int X, final int Y) {

		final Soldier[][] functionsBoard = Board.getGameBoard();
		// if fileLocation is general_red_left.png or general_green_right.png, the animations for them are loaded

		// TODO GENERAL ANIMATION

		// load the corresponding image
		Image solPiece = new Image(new Texture(Gdx.files.internal(fileLocation)));
		// draw the image at the correct position
		solPiece.setSize(80, 80);
		solPiece.setScaling(Scaling.fit);
		System.out.println("Image rendered: file " + fileLocation);

		final Stack tileWidget = new Stack();

		if (health < 15 && health > 0) {
			// Apply a light red hue effect to the tileWidget's image
			Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
			solPiece.setColor(lightRed);
		}

		tileWidget.add(solPiece);

		if (!(health == -1)) {
			// tileWidget is only move able if a Piece is on it, meaning it has health
			tileWidget.setTouchable(Touchable.enabled);
			// draw the health bar
			final ProgressBar healthBar = new ProgressBar(0f, 60f, 1f, false, progressBarSkin);
			healthBar.setSize(0.025F, 0.1F);

			healthBar.setValue(health);
			tileWidget.add(healthBar);

			// hide the health bar by default
			healthBar.setVisible(false);

			// the tileWidget Listener checks if the mouse if over the tile and if yes, displays healthBar
			tileWidget.addListener(new InputListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					healthBar.setVisible(true); // Show the health bar when the mouse enters
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					healthBar.setVisible(false); // Hide the health bar when the mouse exits
				}
			});
		}


		tileWidget.addListener(new InputListener() {
			float offsetX, offsetY;
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				// function activated if the user has clicked somehwere

				// variables changed by the touchDown and touchDragged methods and are used to store positional data
				// for the actor
				// Store the offset between the touch point and the center of the actor
				offsetX = x - 32;
				offsetY = y - 32;
				tileWidget.toFront(); // Bring the actor to the front, so it appears above other actors

				// the icon figurine can only end their movement at a tileWidget that is in validMoveTiles
				// if the tileWidget is not in validMoveTiles, the icon figurine is moved back to its original position

				// TODO

				// and the validMoveTiles are cleared
				clearAllowedTiles();

				return true;
			}

			// function used for moving the actor around the screen - not the input, the actual movement
			// drag overrides touchDragged manually and touchDragged overrides Scene2DUIs touchDragged
			// for better performance and more control over the actual movement
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				// function called if the user is Dragging something
				tileWidget.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);

				// as long as the mouse is pressed down, the actor is moved to the mouse position
				// we calculate the tiles it can move to and highlight these tiles with a slightly red hue
				// the calculated tiles are part of a ArrayList variable that is created at create of the whole programm
				// and gets cleared once we touchDragged the actor to a new position

				// switch statement for deciding which
				// Chess Pieces Class mathMove is used to assign the ArrayList validMoveTiles

				// because the tileWidget doesn't save its Coordinates, we
				// split String fileLocation into only the first part before _ and have our typeOfSoldier variable

				String typeOfSoldier = fileLocation.split("_")[0];

				switch (typeOfSoldier){
					case ("infantry"):
						setAllowedTiles(Infantry.mathMove(X, Y ));
						break;
					case "general":
						setAllowedTiles(General.mathMove(X, Y ));
						break;
					case "wardog":
						setAllowedTiles(Wardog.mathMove(X, Y ));
						break;
					case "helicopter":
						setAllowedTiles(Helicopter.mathMove(X, Y ));
						break;
					case "commando":
						setAllowedTiles(Commando.mathMove(X, Y ));
						break;
					case "tank":
						setAllowedTiles(Tank.mathMove(X, Y ));
						break;
				}
			}
		});
		return tileWidget;
	}

	// method for setting the ArrayList of allowed tiles to move to and a method for clearing it to nothing
	public static void setAllowedTiles (ArrayList<Coordinates> validMoveTiles) {

		// renew the whole Stage inside possibleMovesOverlay to clear the old tiles
		possibleMoveOverlay.clear();	// clear the old tiles

		// start of overlay creation, works similiar to the tileWidget creation

		Table root = new Table();

		root.setSize(720, 640);
		root.center(); // Center the Overlay exactly above the gameBoard in the parent container (stage)
		// refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
		root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
				(Gdx.graphics.getHeight() - root.getHeight()) / 2f);

		// for the size of the tiles
		int tileSize = 80;
		int numRows = 8;
		int numColumns = 9;

		for (int i = 0; i < numRows; i++) {
			// add a new stage Table row after each row of the gameBoard
			root.row();
			for (int j = 0; j < numColumns; j++) {
				// create a new box like widget at each position of the board and add it to the root table
				// it is 80x80 pixels, holds the image of the piece at that position and is movable to other positions
				// switch statement to check which type of piece it is

				// Empty box (no image) or xMarker (red X) if it is a valid move
				// as coordinates generation in touch.dragged-Event
				boolean xMarkerAdded = false;
				for (Coordinates validMoveTile : validMoveTiles) {
					if (validMoveTile.getX() == j && validMoveTile.getY() == i) {
						// Apply a red X
						root.add(new Image(new Texture("xMarker.png"))).size(tileSize);
						xMarkerAdded = true;
						break;
					}
				}
				if (!xMarkerAdded) {
					root.add(new Image(new Texture("empty.png"))).size(tileSize);
				}
			}
		}
		// add the root table to the stage
		possibleMoveOverlay.addActor(root);
		renderOverlay = true;
	}

	public static void clearAllowedTiles () {
		renderOverlay = false;
	}

	// method for creating the stage for the game end ON TOP of the gameStage
	// (changing InputProcessor to stop Game Progress)

	public static void createGameEndStage (String winnerTeamColour){
		Stage gameEndStage = new Stage();

		// change input to gameEndStage so old Back to Main Menu isn't trigger-able as well as Drag and Drop
		Gdx.input.setInputProcessor(gameEndStage);

		// Begin of GameEndLayout - Root Table arranges content automatically and adaptively as ui-structure
		final Table endRoot = new Table();
		endRoot.setFillParent(true);
		gameEndStage.addActor(endRoot);

		// display the winner and a button to return to the main menu.
		Label winnerLabel = new Label("The " + winnerTeamColour + " Team won!", skin);
		endRoot.add(winnerLabel).padBottom(20);
		endRoot.row();

		// back button to return to the main menu
		TextButton backButton = new TextButton("Return To Main Menu", skin);
		endRoot.add(backButton).padBottom(20);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// refresh gameBoard to initial state by going to the mainMenu
				switchToStage(createMainMenuStage());
				// create a new gameBoard since the old one is still in memory
				setGameBoard();
			}
		});
		endRoot.row();

	}
}