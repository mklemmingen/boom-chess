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
	// gameBoard
	public static Soldier[][] gameBoard;
	public static boolean gameInSession;

	// for the tiled map
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	// for determining game stage affairs down the line
	public static String winnerTeamColour;


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

		// for the stages, displays only stage assigned as currentStage, see method switchToStage
		currentStage.act();
		currentStage.draw();

	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
	}

	private static void switchToStage(Stage newStage) {
		// this method removes the currentStage and loads a new one
		currentStage.dispose();
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	public static void addStageToStage(Stage newStage) {
		// this method adds a new stage to the current stage
		currentStage.addActor(newStage.getRoot());
	}

	private static Stage createMainMenuStage() {

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
		Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// stop menu music and start background_music
		menu_music.stop();
		background_music.setLooping(true);
		background_music.play();

		// Begin of GameLayout - Root Table arranges content automatically and adaptively as ui-structure
		Table root = new Table();

		root.setSize(720, 640);
		root.center(); // Center the gameBoard in the parent container (stage)
		// refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
		root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
				(Gdx.graphics.getHeight() - root.getHeight()) / 2f);
		root.setTouchable(Touchable.enabled);

		gameStage.addActor(root);

		// TODO try to implement the game board as a tiled map and the pieces as actors on top of it
		//  combine the tiled map renderer with the stage renderer? Research: addressing individual .tmx tiles in code
		//  - corresponding to the 2D Array Game Board, the pieces on it, their stats as clean health bars.
		//  ----------------------------------------------------------------------------------------------
		//  Actor-Images must be 80x80px. Add Exit-Button at the Bottom right corner of the screen
		//  Actors should be able to be drag-droppable and snap to the grid. They can only move to tiles
		//  their chess characteristics allow them to. This should be checked by the backend, and be send back as tile
		//  coordinates, so the allowed tiles can temporarily be highlighted. If piece is dropped on an allowed tile,
		//  update 2D Array with this new information. End turn.
		//  Calculate Damage of all the current Players pieces onto enemy pieces. All hit pieces should be highlighted.
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
		Soldier[][] gameBoard = Board.initialise();

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
							root.add(drawPiece("general_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("general_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "infantry":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("infantry_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("infantry_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "helicopter":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("helicopter_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "tank":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("tank_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("tank_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "commando":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("commando_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("commando_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "wardog":
						// if the piece is on the red team
						if (gameBoard[j][i].getTeamColor().equals("red")) {
							// load tile and draw image in it
							root.add(drawPiece("war_dog_red_left.png", currentHealth)).size(tileSize);
						}
						// if the piece is on the green team
						else {
							// load tile and draw image in it
							root.add(drawPiece("war_dogs_green_right.png", currentHealth)).size(tileSize);
						}
						break;
					case "empty":
						 // Empty box (no image)
						root.add(drawPiece("empty.png", currentHealth)).size(tileSize);
						break;
				}
			}
		}

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
			}
		});

		return gameStage;
	}

	private static Actor drawPiece(String fileLocation, int health) {
		// load the corresponding image
		Image solPiece = new Image(new Texture(Gdx.files.internal(fileLocation)));
		// draw the image at the correct position
		solPiece.setSize(80, 80);
		solPiece.setScaling(Scaling.fit);
		System.out.println("Image rendered: file " + fileLocation);

		Stack tileWidget = new Stack();
		tileWidget.add(solPiece);


		if (!(health == -1)) {
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
			if (health < 15) {
				// Apply a light red hue effect to the tileWidget's image
				Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
				solPiece.setColor(lightRed);
			}
		}

		// TODO add a listener to each box that checks if it is dragged checks which team is currently in move,
		//  if the picked up piece is of that team: yes - allow drag; no - set piece back to original position

		return tileWidget;
	}

	// TODO implement a function that checks if the piece can be moved to the new position by the pieces rule
	// TODO light up all the tiles that the piece can move to
	// TODO when dragged somehwere run a try loop that calls upon board.update with the coordinates
	//  so long till it returns true

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
				// refresh gameBoard to initial state
				Soldier[][] gameBoard = Board.initialise();
				switchToStage(createMainMenuStage());
			}
		});
		endRoot.row();

	}
}