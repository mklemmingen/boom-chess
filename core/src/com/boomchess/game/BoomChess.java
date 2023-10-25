package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boomchess.game.backend.*;
import com.boomchess.game.frontend.actor.DeathExplosionActor;
import com.boomchess.game.frontend.actor.DottedLineActor;
import com.boomchess.game.frontend.sound.MusicPlaylist;
import com.boomchess.game.frontend.stage.*;
import com.boomchess.game.frontend.sound.RandomSound;

import java.util.ArrayList;


public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters -------------------------------------------------------
	public static SpriteBatch batch;
	// usage for Scene2DUI-skins and stages -------------------------------------------------------
	public static Skin skin;
	public static Skin progressBarSkin;
	private static Stage currentStage;

	// for the Move Overlay ------------------------------------------------------
	public static boolean showMove = false;
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

	// -------------------------------------------------------------------------------------------
	// ---------------------------- Asset Manager -----------------------------------------------
	// -------------------------------------------------------------------------------------------

	// all assets that can appear multiple times on the screen at once get called a Texture, for creating new Image
	// objects down the line
	public static Texture redTank;
	public static Texture redHelicopter;
	public static Texture redWardog;
	public static Texture redGeneral;
	public static Texture redCommando;
	public static Texture redInfantry;
	public static Texture greenTank;
	public static Texture greenHelicopter;
	public static Texture greenWardog;
	public static Texture greenGeneral;
	public static Texture greenCommando;
	public static Texture greenInfantry;
	public static Texture greenArtillery;
	public static Texture redArtillery;
	private static Image redMove;
	private static Image greenMove;
	// background is drawn in a batch, hence Texture
	private static Texture background;
	private static Texture xMarker;
	public static Image boomLogo;

	public static Texture empty;
	public static Texture hill;

	// music
	public static MusicPlaylist background_music;
	public static Music menu_music;

	// universal Buttons -- here for music and sound control

	public static Button playButton;

	public static Button muteButton;

	// for the volume slider

	public static Slider volumeSlider;
	public static Slider soundVolumeSlider;

	// volume of Sounds

	public static float volume = 0.2f;  // variable to store the current MUSIC volume level

	public static float soundVolume = 0.20f;  // variable to store the current SOUND volume level

	// for the labels

	public static Label volumeLabel;

	public static Label soundVolumeLabel;

	// audio table

	public static Table audioTable;

	// RandomSound Objects of Sound Groups to be played by the Pieces if they deal Damage

	public static RandomSound smallArmsSound;
	public static RandomSound bigArmsSound;
	public static RandomSound dogSound;
	public static RandomSound helicopterSound;
	public static RandomSound tankSound;
	public static RandomSound smallExplosionSound;
	public static RandomSound bigExplosionSound;

	// for the medieval mode

	public static RandomSound archerSound;
	public static RandomSound catapultSound;
	public static RandomSound magicSound;
	public static RandomSound knightSound;
	public static RandomSound queenSound;
	public static RandomSound kingSound;

	public static Texture redArcher;
	public static Texture redCatapult;
	public static Texture redMagician;
	public static Texture redKnight;
	public static Texture redTower;
	public static Texture redQueen;
	public static Texture redKing;
	public static Texture greenArcher;
	public static Texture greenCatapult;
	public static Texture greenMagician;
	public static Texture greenKnight;
	public static Texture greenTower;
	public static Texture greenQueen;
	public static Texture greenKing;

	// for the credits
	public MusicPlaylist creditsMusic;

	// for the boolean value if the game is in medieval mode

	public static boolean isMedievalMode = false;

	// -----------------------------------------------------------------------------------------


	@Override
	public void create() {
		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// loading Screen is going till loading complete and main menu starts ----------------------------
		switchToStage(LoadingScreenStage.initalizeUI());

		// skin of the UI --------------------
		// skin (look) of the buttons via a prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));

		// loading all assets -----------------------------------------------------------------------------------

		background = new Texture(Gdx.files.internal("background_5.png"));

		greenMove = new Image(new Texture(Gdx.files.internal("green_Move.png")));
		redMove = new Image(new Texture(Gdx.files.internal("red_Move.png")));
		greenInfantry = new Texture(Gdx.files.internal("infantry_green_right.png"));
		redInfantry = new Texture(Gdx.files.internal("infantry_red_left.png"));
		greenCommando = new Texture(Gdx.files.internal("commando_green_right.png"));
		redCommando = new Texture(Gdx.files.internal("commando_red_left.png"));
		greenGeneral = new Texture(Gdx.files.internal("general_green_right.png"));
		redGeneral = new Texture(Gdx.files.internal("general_red_left.png"));
		greenWardog = new Texture(Gdx.files.internal("war_dog_green_right.png"));
		redWardog = new Texture(Gdx.files.internal("war_dog_red_left.png"));
		greenHelicopter = new Texture(Gdx.files.internal("helicopter_green_right.png"));
		redHelicopter = new Texture(Gdx.files.internal("helicopter_red_left.png"));
		greenTank = new Texture(Gdx.files.internal("tank_green_right.png"));
		redTank = new Texture(Gdx.files.internal("tank_red_left.png"));
		greenArtillery = new Texture(Gdx.files.internal("artillery_green_right.png"));
		redArtillery = new Texture(Gdx.files.internal("artillery_red_left.png"));

		xMarker = new Texture(Gdx.files.internal("xMarker.png"));

		boomLogo = new Image(new Texture(Gdx.files.internal("logo/Logo3.png")));

		empty = new Texture(Gdx.files.internal("empty.png"));

		// Loading Texture of the map
		Image map = new Image(new Texture(Gdx.files.internal("map2/game_map7.png")));

		// load the Textures of the medieval game mode

		redArcher = new Texture(Gdx.files.internal("medieval/red_archer.png"));
		redCatapult = new Texture(Gdx.files.internal("medieval/red_catapult.png"));
		redMagician = new Texture(Gdx.files.internal("medieval/red_magician.png"));
		redKnight = new Texture(Gdx.files.internal("medieval/red_knight.png"));
		redQueen = new Texture(Gdx.files.internal("medieval/red_queen.png"));
		redKing = new Texture(Gdx.files.internal("medieval/red_king.png"));
		greenArcher = new Texture(Gdx.files.internal("medieval/green_archer.png"));
		greenCatapult = new Texture(Gdx.files.internal("medieval/green_catapult.png"));
		greenMagician = new Texture(Gdx.files.internal("medieval/green_magician.png"));
		greenKnight = new Texture(Gdx.files.internal("medieval/green_knight.png"));
		greenQueen = new Texture(Gdx.files.internal("medieval/green_queen.png"));
		greenKing = new Texture(Gdx.files.internal("medieval/green_king.png"));

		// load the sound effects into respective Objects --------------------------------------

		smallArmsSound = new RandomSound();
		smallArmsSound.addSound("sounds/desert-eagle-gunshot.mp3");
		smallArmsSound.addSound("sounds/Gunshot/autocannon-20mm.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Long/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/High/Short/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Short/H.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/A.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/C.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/D.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/E.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/F.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/G.mp3");
		smallArmsSound.addSound("sounds/Gunshot/Low/Long/H.mp3");

		bigArmsSound = new RandomSound();
		bigArmsSound.addSound("sounds/cannonball.mp3");

		dogSound = new RandomSound();
		dogSound.addSound("sounds/Dogs/dog_barking.mp3");

		helicopterSound = new RandomSound();
		helicopterSound.addSound("sounds/helicopter-rotor-loop.mp3");

		tankSound = new RandomSound();
		tankSound.addSound("sounds/tank-engine.mp3");

		smallExplosionSound = new RandomSound();

		bigExplosionSound = new RandomSound();
		bigExplosionSound.addSound("sounds/boom.mp3");

		// load the sounds for the medieval mode

		archerSound = new RandomSound();
		catapultSound = new RandomSound();
		knightSound = new RandomSound();
		magicSound = new RandomSound();
		queenSound = new RandomSound();
		kingSound = new RandomSound();

		// load the background music into MusicPlaylist object --------------------------------------
		background_music = new MusicPlaylist();
		background_music.addSong("music/A Little R & R.mp3");
		background_music.addSong("music/24 Stray cat.mp3");
		background_music.addSong("music/05 Thought Soup.mp3");
		background_music.addSong("music/06 Tonal Dissonance.mp3");
		background_music.addSong("music/27 Coffee Break.mp3");
		background_music.addSong("music/36 Tonal Resonance.mp3");
		background_music.addSong("music/epic-battle.mp3");
		background_music.addSong("music/Outside the Colosseum.mp3");

		// load the menu music

		menu_music = Gdx.audio.newMusic(Gdx.files.internal
				("music/(LOOP-READY) Track 1 - Safe Zone No Intro.mp3"));

		// ---------------------------- universal Buttons for adding to stages

		playButton = new Button(skin, "music");

		muteButton = new Button(skin, "sound");

		// for the style out of the Commodore64 json file - REFERENCE:

		// com.badlogic.gdx.scenes.scene2d.ui.Button$ButtonStyle: {
		//	default: { up: button, down: button-down },
		//	music: { up: music-off, down: music, checked: music },
		//	sound: { up: sound-off, down: sound, checked: sound },
		//	toggle: { up: button, down: button-down, checked: button-down }

		// com.badlogic.gdx.scenes.scene2d.ui.Slider$SliderStyle: {
		//	default-vertical: { background: slider, knob: slider-knob },
		//	default-horizontal: { background: slider, knob: slider-knob }

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// if in game state - play background_music
				if (currentState != GameState.NOT_IN_GAME) {
					if(background_music.isPlaying()) {
						background_music.stop();
						background_music.setVolume(0);
					} else {
						background_music.play();
						background_music.setVolume(volume);
					}
				} else {
					if(menu_music.isPlaying()) {
						menu_music.stop();
						menu_music.setVolume(0);
					} else {
						menu_music.play();
						menu_music.setVolume(volume);
					}
				}
			}
		});

		muteButton.addListener(new ClickListener() {
		   @Override
		   public void clicked(InputEvent event, float x, float y) {
			   // if in game state - play background_music
			   if (volume == 0) {
				   volume = 0.1f;
				   soundVolume = 0.1f;
				   volumeSlider.setValue(0.1f);
				   soundVolumeSlider.setValue(1.0f);
				   if (currentState != GameState.NOT_IN_GAME) {
					   background_music.setVolume(volume);
				   } else {
					   menu_music.setVolume(volume);
				   }
			   } else {
				   volume = 0;
				   soundVolume = 0;
				   volumeSlider.setValue(0);
				   soundVolumeSlider.setValue(0);
				   if (currentState != GameState.NOT_IN_GAME) {
					   background_music.setVolume(volume);
				   } else {
					   menu_music.setVolume(volume);
				   }
			   }
		   }
		});

		// universal volume sliders

		volumeSlider = new Slider(0, 0.4f, 0.01f, false, skin);
		volumeSlider.setValue(0.2f);
		volumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				volume = volumeSlider.getValue();
				if (currentState != GameState.NOT_IN_GAME) {
					background_music.setVolume(volume);
				} else {
					menu_music.setVolume(volume);
				}
			}
		});

		soundVolumeSlider = new Slider(0, 0.4f, 0.01f, false, skin);
		soundVolumeSlider.setValue(0.2f);

		soundVolumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundVolume = soundVolumeSlider.getValue();
				smallArmsSound.setVolume(soundVolume);
				bigArmsSound.setVolume(soundVolume);
				dogSound.setVolume(soundVolume);
				helicopterSound.setVolume(soundVolume);
				tankSound.setVolume(soundVolume);
				smallExplosionSound.setVolume(soundVolume);
				bigExplosionSound.setVolume(soundVolume);
			}
		});

		// their labels

		volumeLabel = new Label("Music", skin);
		soundVolumeLabel = new Label("Sound", skin);

		// ----------------------------

		// adding mapStage
		// for the  map used as the chess board

		mapStage = new Stage(new ScreenViewport());

		// Center the map on the screen
		map.setPosition((float) Gdx.graphics.getWidth() /2 - map.getWidth()/2,
				(float) Gdx.graphics.getHeight() /2 - map.getHeight()/2);

		// Add a gray hue to the map
		map.setColor(0.5f, 0.5f, 0.5f, 1f);  // apply a grey tint to the map

		mapStage.addActor(map);


		// -----------------------------------------------------------------------------------------
		// creation of the camera fitting to the set resolution in DesktopLauncher

		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 1536, 880);

		// for the dotted Line when damage occurs -----------------------------------------------

		shapeRenderer = new ShapeRenderer();
		dottedLineStage = new Stage(new ScreenViewport());

		// for the deathExplosion ---------------------------------------------------------------
		deathExplosionStage = new Stage(new ScreenViewport());

		// --------- Audio Table and Stage ------------

		audioTable = new Table();
		audioTable.setPosition(125, 150);

		// Buttons
		audioTable.add(playButton);
		audioTable.add(muteButton);
		audioTable.row();

		// Volume Slider
		// Label colour #4242E7
		volumeLabel.setColor(Color.valueOf("#4242E7"));
		audioTable.add(volumeLabel).size(50, 20).padRight(40);
		audioTable.add(volumeSlider);
		audioTable.row();

		// Sound Volume Slider
		// Label colour #4242E7
		soundVolumeLabel.setColor(Color.valueOf("#4242E7"));
		audioTable.add(soundVolumeLabel).size(50, 20).padRight(40);
		audioTable.add(soundVolumeSlider);
		audioTable.row();

		// -----------------------------------------------------------------------------
		/*
		 * creation of the stages for the menu - this allows the Scene2D.ui to be used for quick swapping of screens
		 * and the usage of the buttons/ui-elements/so-called actors and child actors to be used
		 * stages will be the way we display all menus and the game itself
		 */

		// skin (look) of the progress bar via a prearranged json file
		progressBarSkin = new Skin(Gdx.files.internal("progressBarSkin/neon-ui.json"));

		// for the Move Logo Overlay
		moveLogoStage = new Stage();

		// creation of empty Board.validMoveTiles for null-pointer exception avoidance
		Board.validMoveTiles = new ArrayList<>();

		// ensures game starts in menu
		createMainMenuStage();
	}


	public enum GameState {
		// for determining the current state of the game
		RED_TURN, GREEN_TURN, NOT_IN_GAME
	}

	public static GameState currentState = GameState.NOT_IN_GAME;
	
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
				currentMover.addActor(redMove);
			} else if (currentState == GameState.GREEN_TURN) {
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

		// for the audio table - add it to currentStage
		currentStage.addActor(audioTable);

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
				// for checking if the piece is an artillery -> call checkSurroundingsArtiller
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

		// dispose of all assets --- Textures do not natively get rubbish canned by Javas inbuilt collector
		// Images do tho!
		 redTank.dispose();
		 redHelicopter.dispose();
		 redWardog.dispose();
		 redGeneral.dispose();
		 redCommando.dispose();
		 redInfantry.dispose();
		 greenTank.dispose();
		 greenHelicopter.dispose();
		 greenWardog.dispose();
		 greenGeneral.dispose();
		 greenCommando.dispose();
		 greenInfantry.dispose();
		 xMarker.dispose();
		 empty.dispose();
		 hill.dispose();
	}

	public static void switchToStage(Stage newStage) {
		// this method removes the currentStage and loads a new one
		// used in the Stage classes at the end to load the created Stages
		if (currentStage != null){
			currentStage.clear();}
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	public static void reRenderGame(){
		switchToStage(GameStage.createGameStage(isBotMatch));
	}

	public static void createMainMenuStage() {
		switchToStage(MenuStage.initializeUI());
	}

	public static void createHelpStage() {
		switchToStage(HelpStage.initializeUI());
	}

	public static void createOptionsStage() {
		switchToStage(OptionsStage.initalizeUI());
	}

	public static void createCreditsStage() {
		switchToStage(CreditsStage.initializeUI());
	}

	public static void createGameEndStage (String winnerTeamColour){
		// method for creating the stage for the game end ON TOP of the gameStage
		// (changing InputProcessor to stop Game Progress)
		addToStage(GameEndStage.initializeUI(winnerTeamColour));
	}

	public static void addToStage (Actor actorToAdd){
		// method for adding a stage to the currentStage
		currentStage.addActor(actorToAdd);
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
						root.add(new Image(xMarker)).size(tileSize);
						xMarkerAdded = true;
						break;
					}
				}
				if (!xMarkerAdded) {
					root.add(new Image(empty)).size(tileSize);
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

	public static Coordinates calculateTileByPX(int pxCoordinateX, int pxCoordinateY) {

		// BUGFIX! In LibGDX, the origin of the screen is the top left! i traditional, its bottom left!

		// method for checking which tile a pxCoordinateX and pxCoordinateY is in, creating the coordinates object
		// of the respective tile and returning it
		Coordinates iconTileCoordinate = new Coordinates();

		// we calculate this by first getting the screen width and height
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		// we then calculate the upper left corner of the gameBoard by subtracting the screenWidth and screenHeight by
		// the gameBoard width and height and then dividing it by 2a
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