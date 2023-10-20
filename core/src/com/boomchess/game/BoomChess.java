package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;


public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters -------------------------------------------------------
	private static SpriteBatch batch;
	// loading of essential background images -------------------------------------------------------------
	private Texture background;
	// start of asset loading Sound and Music ----------------------------------------------------------
	public static Sound boom;
	public static Music background_music;
	public static Music menu_music;
	// usage for Scene2DUI-skins and stages -------------------------------------------------------
	private static Skin skin;
	private static Skin progressBarSkin;
	private static Stage currentStage;

	// for the Move Overlay ------------------------------------------------------
	private static boolean showMove = false;
	private static Stage  moveLogoStage;

	// for the  map -----------------------------------------
	Stage mapStage;

	// for setting the current "Mover" of the game / if a Move has been valid ------------------------------
	public static boolean legitTurn = false;

	// for the x-marker overlay over the game-field -----------------------------------------------
	public static boolean renderOverlay = false;
	public static Stage possibleMoveOverlay;

	// for bot matching -----------------------------------------------
	public static boolean isBotMatch;

	// used for the dotted line when damage occurs -----------------------------------------------
	// Shape Renderer for easy drawing of lines
	private static ShapeRenderer shapeRenderer;
	// stage we render the shapes on
	private static Stage dottedLineStage;
	// used for the deathExplosion ---------------------------------------------
	private static Stage deathExplosionStage;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background_4.png");

		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// creation of the camera fitting to the set resolution in DesktopLauncher

		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 1536, 880);

		// for the dotted Line when damage occurs -----------------------------------------------
		shapeRenderer = new ShapeRenderer();
		dottedLineStage = new Stage(new ScreenViewport());

		// for the deathExplosion ---------------------------------------------------------------
		deathExplosionStage = new Stage(new ScreenViewport());

		// load the boom sound effect and background music --------------------------------------
		boom = Gdx.audio.newSound(Gdx.files.internal("sounds/boom.ogg"));

		background_music = Gdx.audio.newMusic(Gdx.files.internal("music/05 Thought Soup.ogg"));
		Music game_music2 = Gdx.audio.newMusic(Gdx.files.internal("music/06 Tonal Dissonance.ogg"));
		Music game_music3 = Gdx.audio.newMusic(Gdx.files.internal("music/24 Stray Cat.ogg"));
		Music game_music4 = Gdx.audio.newMusic(Gdx.files.internal("music/27 Coffee Break.ogg"));
		Music game_music5 = Gdx.audio.newMusic(Gdx.files.internal("music/36 Tonal Resonance.ogg"));
		Music game_music6 = Gdx.audio.newMusic(Gdx.files.internal("music/epic-battle.mp3"));
		Music game_music7 = Gdx.audio.newMusic(Gdx.files.internal("music/Outside the Colosseum.ogg"));

		// load the menu music

		menu_music = Gdx.audio.newMusic(Gdx.files.internal("music/(LOOP-READY) Track 1 - Safe Zone No Intro.mp3"));
		Music menu_music2 = Gdx.audio.newMusic(Gdx.files.internal("music/A Little R & R.ogg"));

		// for the  map used as the chess board -----------------------------------------

		mapStage = new Stage(new ScreenViewport());

		// Loading Texture of the map
		Image map = new Image(new Texture(Gdx.files.internal("map2/game_map7.png")));

		// Center the map on the screen
		map.setPosition((float) Gdx.graphics.getWidth() /2 - map.getWidth()/2,
				(float) Gdx.graphics.getHeight() /2 - map.getHeight()/2);

		// Add a gray hue to the map
		map.setColor(0.5f, 0.5f, 0.5f, 1f);  // apply a grey tint to the map

		mapStage.addActor(map);

		// -----------------------------------------------------------------------------
		/*
		 * creation of the stages for the menu - this allows the Scene2D.ui to be used for quick swapping of screens
		 * and the usage of the buttons/ui-elements/so-called actors and child actors to be used
		 * stages will be the way we display all menus and the game itself
		 */

		// skin (look) of the buttons via a prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));
		// skin (look) of the progress bar via a prearranged json file
		progressBarSkin = new Skin(Gdx.files.internal("progressBarSkin/neon-ui.json"));

		// create the big game Board as an object of the Board class
		Board.initialise();

		// ensures game starts in menu
		currentStage = createMainMenuStage();

		// for the Move Logo Overlay
		moveLogoStage = new Stage();

		// creation of empty Board.validMoveTiles for null-pointer exception avoidance
		Board.validMoveTiles = new ArrayList<>();
	}


	enum GameState {
		// for determining the current state of the game
		RED_TURN, GREEN_TURN, NOT_IN_GAME
	}

	static GameState currentState = GameState.NOT_IN_GAME;
	
	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		// used for the dotted line when damage occurs (clears screen)  ------------------------
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();


		// start of the turn based system ----------------------------
		if (showMove){

			// for the map
			mapStage.act();
			mapStage.draw();
			moveLogoStage.clear();


			// this method adds a new stage to the currentStage
			// Image of the currentMover
			Table currentMover = new Table();
			currentMover.setSize(250, 125);
			// set position to the upper far left corner of the screen
			currentMover.setPosition(30, Gdx.graphics.getHeight() - 150);

			if (currentState == GameState.RED_TURN) {
				Image redMove = new Image(new Texture(Gdx.files.internal("red_Move.png")));
				currentMover.addActor(redMove);
			} else if (currentState == GameState.GREEN_TURN) {
				Image greenMove = new Image(new Texture(Gdx.files.internal("green_Move.png")));
				currentMover.addActor(greenMove);
			}
			moveLogoStage.addActor(currentMover);

			moveLogoStage.act();
			moveLogoStage.draw();
		}

		// for the overlay of possible moves
		if (renderOverlay) {
			possibleMoveOverlay.act();
			possibleMoveOverlay.draw();
		}

		// for the stages, displays only stage assigned as currentStage, see method switchToStage
		currentStage.getViewport().apply();
		currentStage.act();
		currentStage.draw();

		// for the dotted line when damage occurs -----------------------------------------------
		// Render the dottedLineStage
		dottedLineStage.getViewport().apply();
		dottedLineStage.act(Gdx.graphics.getDeltaTime());
		dottedLineStage.draw();

		// for the deathExplosion --------------------------------------------------------------
		// Render the deathExplosionStage
		deathExplosionStage.getViewport().apply();
		deathExplosionStage.act(Gdx.graphics.getDeltaTime());
		deathExplosionStage.draw();

		processTurn();


	}

	private void processTurn() {
		if (currentState == GameState.RED_TURN) {
			if (legitTurn) {
				calculateDamage("red");
				switchTurn(currentState);
				legitTurn = false;
			}
		} else if (currentState == GameState.GREEN_TURN) {
			if (legitTurn) {
				calculateDamage("green");
				switchTurn(currentState);
				legitTurn = false;
			}
		}
	}



	private void calculateDamage(String teamColor) {
		// method that goes through each tile of the board and if SoldierTeam is teamColor,
		// lets it attack the surrounding tiles
		Soldier[][] gameBoard = Board.getGameBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				Soldier soldier = gameBoard[i][j];
				if (soldier != null && soldier.getTeamColor().equals(teamColor)) {
					Damage.checkSurroundings(i, j);
				}
			}
		}
	}

	private void switchTurn(GameState state) {
		if (state == GameState.RED_TURN) {
			currentState = GameState.GREEN_TURN;
		} else {
			currentState = GameState.RED_TURN;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
		possibleMoveOverlay.dispose();
		moveLogoStage.dispose();
		shapeRenderer.dispose();
		dottedLineStage.dispose();
		deathExplosionStage.dispose();
	}

	private static void switchToStage(Stage newStage) {
		// this method removes the currentStage and loads a new one
		currentStage.clear();
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	private static Stage createMainMenuStage() {

		showMove = false;

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

		final Image title = new Image(new Texture("logo/Logo1.png"));
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
				// create the first gameBoard
				setGameBoard();

				// stop menu music and start background_music
				menu_music.stop();
				background_music.setLooping(true);
				background_music.play();

				currentState = GameState.RED_TURN;
				
				boolean isBotMatch = false;
				switchToStage(createGameStage(isBotMatch));
			}
		});
		root.row();

		TextButton playBotButton = new TextButton("Play a Game Against the Computer", skin);
		root.add(playBotButton).padBottom(20);
		playBotButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// create the first gameBoard
				setGameBoard();

				// stop menu music and start background_music
				menu_music.stop();
				background_music.setLooping(true);
				background_music.play();

				currentState = GameState.RED_TURN;
				
				boolean isBotMatch = true;
				switchToStage(createGameStage(isBotMatch));
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

		if (isBotMatch){
			System.out.println("Bot Match");
			// TODO CREATE MULTIPLE BOT DIFFICULTIES
		}

		showMove = true;

		// addLogo(); TODO remove this variable long term
		
		Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// xMarkerOverlay
		possibleMoveOverlay = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// CHECKED try to implement the game board as a tiled map and the pieces as actors on top of it
		//  combine the tiled map renderer with the stage renderer? Research: addressing individual .tmx tiles in code
		//  - corresponding to the 2D Array Game Board, the pieces on it, their stats as clean health bars.
		//  ----------------------------------------------------------------------------------------------
		//  Actor-Images must be 80x80px. Add Exit-Button in the Bottom right corner of the screen
		//  Actors should be able to be drag-droppable and snap to the grid. They can only move to tiles
		//  their chess characteristics allow them to. This should be checked by the backend, and be sent back as tile
		//  coordinates, so the allowed tiles can temporarily be highlighted. If piece is dropped on an allowed tile,
		//  update 2D Array with this new information. End turn.
		//  Calculate Damage from all the current Players pieces onto enemy pieces. All hit pieces should be highlighted.
		//  If a piece is killed, remove it from the board and the 2D Array. If a piece is killed, check if that piece
		//  was the general and end the game by saving who won (who killed the other general).
		//  If not, switch to the next player.
		//  If yes, create Game-End-Stage: display the winner and a button to return to the main menu.

		// render the gameBoard by iterating through the 2D Array Soldiers[][] gameBoard
		// checking which type of piece at position and drawing the correct image there

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
				currentState = GameState.NOT_IN_GAME;
				setGameBoard();
			}
		});

		return gameStage;
	}

	private static void setGameBoard() {
		Board.initialise();
	}

	public static Table drawTheGameBoard() {

		Soldier[][] gameBoard = Board.getGameBoard();

		// Begin of GameLayout - Root Table arranges content automatically and adaptively as ui-structure
		Table root = new Table();

		root.setSize(720, 640);
		root.center(); // Center the gameBoard in the parent container (stage)
		// refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
		root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
				(Gdx.graphics.getHeight() - root.getHeight()) / 2f);


		// for the size of the tiles
		int tileSize = 80;
		int numRows = 8;
		int numColumns = 9;

		batch.begin();

		for (int i = 0; i < numRows; i++) {
			// add a new stage Table row after each row of the gameBoard
			root.row();
			for (int j = 0; j < numColumns; j++) {
				// create a new box like widget at each position of the board and add it to the root table
				// it is 80x80 pixels, holds the image of the piece at that position and is movable to other positions
				// switch statement to check which type of piece it is
				
				switch (gameBoard[j][i].getSoldierType()) {
					case "general":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("general_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("general_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "infantry":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("infantry_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("infantry_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "helicopter":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "tank":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("tank_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("tank_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "commando":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("commando_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("commando_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "wardog":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("war_dog_red_left.png", j, i
							)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("war_dog_green_right.png", j, i
							)).size(tileSize);
						}
						break;
					case "empty":
						// Empty box (no image)
						root.add(drawPiece("empty.png", j, i)).size(tileSize);
						break;
				}
			}
		}
		System.out.println("\n New Board has been rendered.");
		batch.end();
		return root;
	}

	private static Actor drawPiece(final String fileLocation,
								   final int X, final int Y) {

		// if fileLocation is general_red_left.png or general_green_right.png, the animations for them are loaded

		// TODO GENERAL ANIMATION
		
		Soldier[][] gameBoard = Board.getGameBoard();
		int health = gameBoard[X][Y].getHealth();

		// load the corresponding image
		Image solPiece = new Image(new Texture(Gdx.files.internal(fileLocation)));
		// draw the image at the correct position
		solPiece.setSize(80, 80);
		solPiece.setScaling(Scaling.fit);

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

			// the tileWidget Listener checks if the mouse is over the tile and if yes, displays healthBar
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


		tileWidget.addListener(new DragListener() {
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				// Code runs when dragging starts:
				System.out.println("\n Started dragging the actor!");

				// Get the team color of the current tile
				Soldier[][] gameBoard = Board.getGameBoard();
				String tileTeamColor = gameBoard[X][Y].getTeamColor();

				// If it's not the current team's turn, cancel the drag and return
				if ((currentState == GameState.RED_TURN && !tileTeamColor.equals("red")) ||
						(currentState == GameState.GREEN_TURN && !tileTeamColor.equals("green"))) {
					event.cancel();
					System.out.println("\n It's not your turn!");
					reRenderGame();
					return;
				}

				tileWidget.toFront(); // Bring the actor to the front, so it appears above other actors
				// as long as the mouse is pressed down, the actor is moved to the mouse position
				// we calculate the tiles it can move to and highlight these tiles with a slightly red hue
				// the calculated tiles are part of a ArrayList variable that is created at create of the whole programm
				// and gets cleared once we touchDragged the actor to a new position

				// switch statement for deciding which
				// Chess Pieces Class mathMove is used to assign the ArrayList validMoveTiles

				// because the tileWidget doesn't save its Coordinates, we
				// split String fileLocation into only the first part before _ and have our typeOfSoldier variable

				String typeOfSoldier = fileLocation.split("_")[0];

				switch (typeOfSoldier) {
					case ("infantry"):
						setAllowedTiles(Infantry.mathMove(X, Y));
						break;
					case "general":
						setAllowedTiles(General.mathMove(X, Y));
						break;
					case "wardog":
						setAllowedTiles(Wardog.mathMove(X, Y));
						break;
					case "helicopter":
						setAllowedTiles(Helicopter.mathMove(X, Y));
						break;
					case "commando":
						setAllowedTiles(Commando.mathMove(X, Y));
						break;
					case "tank":
						setAllowedTiles(Tank.mathMove(X, Y));
						break;
				}
			}

			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				// Code here will run during the dragging
				tileWidget.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				// Code here will run when the player lets go of the actor

				// Get the position of the tileWidget relative to the parent actor (the gameBoard)
				Vector2 localCoords = new Vector2(x, y);
				// Convert the position to stage (screen) coordinates
				Vector2 screenCoords = tileWidget.localToStageCoordinates(localCoords);

				System.out.println("\n Drag stopped at screen position: " + screenCoords.x + ", " + screenCoords.y);

				Coordinates currentCoord = calculateTileByPX((int) screenCoords.x, (int) screenCoords.y);

				// for loop through validMoveTiles, at each tile we check for equality of currentCoord with the Coordinate
				// in the ArrayList by using currentCoord.checkEqual(validMoveTiles[i]) and if true, we set the
				// validMove Variable to true, call on the update method of the Board class and break the for loop
				// then clear the Board.


				ArrayList<Coordinates> validMoveTiles = Board.getValidMoveTiles();
				for (Coordinates validMoveTile : validMoveTiles) {
					if (currentCoord.checkEqual(validMoveTile)) {
						// Board.update with oldX, oldY, newX, newY
						Board.update(X, Y, currentCoord.getX(), currentCoord.getY());
						legitTurn = true;
						break;
					}
				}

				// and the validMoveTiles are cleared
				clearAllowedTiles(); // for turning off the Overlay
				Board.emptyValidMoveTiles();
				reRenderGame();

			}
		});


		return tileWidget;
	}

	public static void reRenderGame(){
		// method for clearing and recreating gameStage
		System.out.println("\n reRenderGame called.");
		switchToStage(createGameStage(isBotMatch));
	}

	// method for setting the ArrayList of allowed tiles to move to and a method for clearing it to nothing
	public static void setAllowedTiles (ArrayList<Coordinates> validMoveTiles) {

		Board.setValidMoveTiles(validMoveTiles);
		// renew the whole Stage inside possibleMovesOverlay to clear the old tiles
		possibleMoveOverlay.clear();	// clear the old tiles

		// start of overlay creation, works similar to the tileWidget creation

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

		currentState = GameState.NOT_IN_GAME;

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

	public static Coordinates calculateTileByPX(int pxCoordinateX, int pxCoordinateY) {

		// BUGFIX! In LibGDX, the origin of the screen is the top left! i traditional, its bottom left!

		// method for checking which tile a pxCoordinateX and pxCoordinateY is in, creating the coordinates object
		// of the respective tile and returning it
		Coordinates iconTileCoordinate = new Coordinates();

		// we calculate this by first getting the screen width and height
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we then calculate the upper left corner of the gameBoard by subtracting the screenWidth and screenHeight by
		// the gameBoard width and height and then dividing it by 2
		int upperLeftCornerX = (screenWidth - 720) / 2;
		int upperLeftCornerY = (screenHeight - 640) / 2;

		// Adjust the pxCoordinate by adding 1 to ensure boundary pixels are correctly classified.
		int adjustedPxX = pxCoordinateX + 1;
		int adjustedPxY = pxCoordinateY + 1;

		// Adjust the Y-coordinate to reflect the difference in coordinate systems ( see BUGFIX )
		adjustedPxY = screenHeight - adjustedPxY;

		// we then calculate the tileX and tileY by subtracting the upperLeftCornerX and upperLeftCornerY from the
		// adjustedPxX and adjustedPxY and dividing it by the tile size
		int tileX = (adjustedPxX - upperLeftCornerX) / 80;
		int tileY = (adjustedPxY - upperLeftCornerY) / 80;

		// we then set the tileX and tileY in the iconTileCoordinate object
		iconTileCoordinate.setX(tileX);
		iconTileCoordinate.setY(tileY);

		return iconTileCoordinate;
	}


	// --------------------------------------------------------------------------------------------------------------
	// ------------------------------------------- DAMAGE ANIMATION METHODS -----------------------------------------
	// --------------------------------------------------------------------------------------------------------------

	// in this first method, the pixel coordinates of the center of a tile is calculated
	// this will be used afterwards to create a dotted Line Animation from an attacker to a defender tile
	public static Coordinates calculatePXbyTile(int tilePositionX, int tilePositionY){
		Coordinates pxCoords = new Coordinates();
		int tileWidth = 80, tileHeight = 80;

		// we calculate the board dimensions based on tile dimensions and the number of tiles
		float boardWidth = 9 * tileWidth;
		float boardHeight = 8 * tileHeight;

		// we use the same way as in calculateTileByPX to get the Screen Parameters
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we calculate the top left corner pixel coordinate of the board, which we use for the calculation
		float boardStartX = (screenWidth - boardWidth) / 2;
		float boardStartY = (screenHeight - boardHeight) / 2;

		// Invert the tilePositionY for libGDX coordinate System compliance
		int invertedTilePositionY = 7 - tilePositionY;

		// we calculate the pixel coordinates of any tile
		float tilePixelX = boardStartX + tilePositionX * tileWidth + ((float) tileWidth / 2);
		float tilePixelY = boardStartY + invertedTilePositionY * tileHeight + ((float) tileHeight / 2);



		// we set the calculated px coords into a Coordinates object
		pxCoords.setCoordinates((int) tilePixelX, (int) tilePixelY);

		return pxCoords;
	}

	// for adding a DottedLine to the dottedLineStage
	public static void addDottedLine(float x1, float y1, float x2, float y2) {
		DottedLineActor lineActor = new DottedLineActor(x1, y1, x2, y2, shapeRenderer);
		dottedLineStage.addActor(lineActor);
	}

	// for the fitted viewport once the screen gets resized
	@Override
	public void resize(int width, int height) {
		currentStage.getViewport().update(width, height, true);
		dottedLineStage.getViewport().update(width, height, true);
		deathExplosionStage.getViewport().update(width, height, true);
	}

	// --------------------------------------------------------------------------------------------------------------
	// ------------------------------------------- deathAnimation METHODS -------------------------------------------
	// --------------------------------------------------------------------------------------------------------------
	public static Coordinates calculatePXbyTileNonGDX(int tilePositionX, int tilePositionY){
		Coordinates pxCoords = new Coordinates();
		int tileWidth = 80, tileHeight = 80;

		// we calculate the board dimensions based on tile dimensions and the number of tiles
		float boardWidth = 9 * tileWidth;
		float boardHeight = 8 * tileHeight;

		// we use the same way as in calculateTileByPX to get the Screen Parameters
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we calculate the top left corner pixel coordinate of the board, which we use for the calculation
		float boardStartX = (screenWidth - boardWidth) / 2;
		float boardStartY = (screenHeight - boardHeight) / 2;

		// Invert the tilePositionY for libGDX coordinate System compliance
		int invertedTilePositionY = 7 - tilePositionY;

		// we calculate the pixel coordinates of any tile
		float tilePixelX = boardStartX + tilePositionX * tileWidth;
		float tilePixelY = boardStartY + invertedTilePositionY * tileHeight;



		// we set the calculated px coords into a Coordinates object
		pxCoords.setCoordinates((int) tilePixelX, (int) tilePixelY);

		return pxCoords;
	}
	public static void addDeathAnimation(int x, int y) {
		DeathExplosionActor deathActor = new DeathExplosionActor(x, y);
		deathExplosionStage.addActor(deathActor);
		System.out.println("Exploded someone at position "+ x + "-" + y);
	}
}