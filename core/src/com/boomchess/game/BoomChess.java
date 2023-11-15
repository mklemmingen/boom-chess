package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.boomchess.game.frontend.actor.HitMarkerActor;
import com.boomchess.game.frontend.moveBotTile;
import com.boomchess.game.frontend.stage.GameEndStage;
import com.boomchess.game.frontend.picture.RandomImage;
import com.boomchess.game.frontend.sound.MusicPlaylist;
import com.boomchess.game.frontend.stage.*;
import com.boomchess.game.frontend.sound.RandomSound;
import java.util.ArrayList;
import static com.boomchess.game.frontend.stage.GameStage.createGameStage;

public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters -------------------------------------------------------
	public static SpriteBatch batch;
	// usage for Scene2DUI-skins and stages -------------------------------------------------------
	// size of tiles on the board
	public static float tileSize;
	public static Skin skin;
	public static Skin progressBarSkin;
	public static float numberObstacle; // number of obstacles in the default game mode
	private static Stage currentStage;

	// for the Move Overlay ------------------------------------------------------
	public static boolean showMove = false;
	private static Stage  moveLogoStage;

	// for the  map -----------------------------------------

	public static Stage mapStage;

	public static RandomImage medievalMaps;
	public static RandomImage modernMaps;

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
	public static Stage deathExplosionStage;

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
	public static Texture blueArtillery;
	public static Texture blueInfantry;
	public static Texture blueCommando;
	public static Texture blueGeneral;
	public static Texture blueWardog;
	public static Texture blueHelicopter;
	public static Texture blueTank;

	private static Image redMove;
	private static Image greenMove;
	private static Image blueMove;

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

	public static Button skipButton;

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
	public static Texture redQueen;
	public static Texture redKing;
	public static Texture greenArcher;
	public static Texture greenCatapult;
	public static Texture greenMagician;
	public static Texture greenKnight;
	public static Texture greenQueen;
	public static Texture greenKing;
	public static Texture greenFea;
	public static Texture redFea;

	// random Texture Objects for the obstacles as well as the obstacle Texture loaded into it
	// for use in the hill constructor

	public static RandomImage obstacleTextures;

	// for the credits
	public MusicPlaylist creditsMusic;

	// for the boolean value if the game is in medieval mode

	public static boolean isMedievalMode = false;

	public static boolean isColourChanged;

	public static Sound loadingSound;

	public static String botDifficulty = "easy";

	// stage for gameEnd
	public static Stage gameEndStage;
	
	// BotMove Input Processor
	// public static BotMove botMove;

	// botMove class
	public static moveBotTile botMove;

	// variables for empty coordinate for botmove

	public static boolean useEmpty;
	public static int emptyX;
	public static int emptyY;

	// -----------------------------------------------------------------------------------------


	@Override
	public void create() {
		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// loading Screen is going till loading complete and main menu starts ----------------------------
		loadingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/countdown.mp3"));
		switchToStage(LoadingScreenStage.initalizeUI());

		// for defaulting colour change
		isColourChanged = false;

		// skin of the UI --------------------
		// skin (look) of the buttons via a prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));

		// loading all assets -----------------------------------------------------------------------------------

		background = new Texture(Gdx.files.internal("backgrounds/background_5.png"));

		greenMove = new Image(new Texture(Gdx.files.internal("moveLogos/green_Move.png")));
		redMove = new Image(new Texture(Gdx.files.internal("moveLogos/red_Move.png")));
		blueMove = new Image(new Texture(Gdx.files.internal("moveLogos/blue_Move.png")));

		greenInfantry = new Texture(Gdx.files.internal("greenTeam/infantry_green_right.png"));
		redInfantry = new Texture(Gdx.files.internal("redTeam/infantry_red_left.png"));
		greenCommando = new Texture(Gdx.files.internal("greenTeam/commando_green_right.png"));
		redCommando = new Texture(Gdx.files.internal("redTeam/commando_red_left.png"));
		greenGeneral = new Texture(Gdx.files.internal("greenTeam/general_green_right.png"));
		redGeneral = new Texture(Gdx.files.internal("redTeam/general_red_left.png"));
		greenWardog = new Texture(Gdx.files.internal("greenTeam/war_dog_green_right.png"));
		redWardog = new Texture(Gdx.files.internal("redTeam/war_dog_red_left.png"));
		greenHelicopter = new Texture(Gdx.files.internal("greenTeam/helicopter_green_right.png"));
		redHelicopter = new Texture(Gdx.files.internal("redTeam/helicopter_red_left.png"));
		greenTank = new Texture(Gdx.files.internal("greenTeam/tank_green_right.png"));
		redTank = new Texture(Gdx.files.internal("redTeam/tank_red_left.png"));
		greenArtillery = new Texture(Gdx.files.internal("greenTeam/artillery_green_right.png"));
		redArtillery = new Texture(Gdx.files.internal("redTeam/artillery_red_left.png"));


		blueArtillery = new Texture(Gdx.files.internal("blueTeam/artillery_blue_right.png"));
		blueInfantry = new Texture(Gdx.files.internal("blueTeam/infantry_blue_right.png"));
		blueCommando = new Texture(Gdx.files.internal("blueTeam/commando_blue_right.png"));
		blueGeneral = new Texture(Gdx.files.internal("blueTeam/general_blue_right.png"));
		blueWardog = new Texture(Gdx.files.internal("blueTeam/war_dog_blue_right.png"));
		blueHelicopter = new Texture(Gdx.files.internal("blueTeam/helicopter_blue_right.png"));
		blueTank = new Texture(Gdx.files.internal("blueTeam/tank_blue_right.png"));

		xMarker = new Texture(Gdx.files.internal("Misc/xMarker.png"));

		boomLogo = new Image(new Texture(Gdx.files.internal("logo/Logo3.png")));

		empty = new Texture(Gdx.files.internal("Misc/empty.png"));

		// Loading Texture of the map

		mapStage  = new Stage();
		
		medievalMaps = new RandomImage();
		modernMaps = new RandomImage();

		medievalMaps.addTexture("map/map2/game_map.png"); // colourful medieval map
		modernMaps.addTexture("map/map2/game_map2.png"); // city map
		modernMaps.addTexture("map/map2/game_map3.png"); // city map
		medievalMaps.addTexture("map/map2/game_map4.png"); // colourful village map
		medievalMaps.addTexture("map/map2/game_map5.png"); // colourful village map
		modernMaps.addTexture("map/map2/game_map6.png"); // desert City map
		modernMaps.addTexture("map/map2/game_map7.png"); // desert City map

		modernMaps.addTexture("map/map3/map1.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map2.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map3.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map4.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map5.png"); // cool black and white map
		modernMaps.addTexture("map/map3/map6.png"); // cool black and white map

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
		redFea = new Texture(Gdx.files.internal("medieval/red_fea.png"));
		greenFea = new Texture(Gdx.files.internal("medieval/green_fea.png"));

		// textures of the obstacles

		obstacleTextures = new RandomImage();
		obstacleTextures.addTexture("obstacles/obstacle1.png");
		obstacleTextures.addTexture("obstacles/obstacle2.png");
		obstacleTextures.addTexture("obstacles/obstacle3.png");
		obstacleTextures.addTexture("obstacles/obstacle4.png");
		obstacleTextures.addTexture("obstacles/obstacle5.png");
		obstacleTextures.addTexture("obstacles/obstacle6.png");
		obstacleTextures.addTexture("obstacles/obstacle7.png");
		obstacleTextures.addTexture("obstacles/obstacle8.png");
		obstacleTextures.addTexture("obstacles/obstacle9.png");
		obstacleTextures.addTexture("obstacles/obstacle10.png");
		obstacleTextures.addTexture("obstacles/obstacle11.png");

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
		background_music.addSong("music/Breakdown.mp3"); // song added by Artist Wumbatz
		background_music.addSong("music/A Little R & R.mp3");
		background_music.addSong("music/24 Stray cat.mp3");
		background_music.addSong("music/05 Thought Soup.mp3");
		background_music.addSong("music/06 Tonal Dissonance.mp3");
		background_music.addSong("music/27 Coffee Break.mp3");
		background_music.addSong("music/36 Tonal Resonance.mp3");
		background_music.addSong("music/epic-battle.mp3");
		background_music.addSong("music/Outside the Colosseum.mp3");
		background_music.addSong("music/Song Idee Chess.mp3"); // song added by Artist Wumbatz
		background_music.addSong("music/Song 2.mp3"); // song added by Artist Wumbatz


		// load the menu music

		menu_music = Gdx.audio.newMusic(Gdx.files.internal
				("music/(LOOP-READY) Track 1 - Safe Zone No Intro.mp3"));

		// ---------------------------- universal Buttons for adding to stages

		playButton = new Button(skin, "music");

		muteButton = new Button(skin, "sound");

		skipButton = new TextButton("skip", skin);

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
				// this is the Button for playing and pausing the music (it switches current State)
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

		skipButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentState != GameState.NOT_IN_GAME) {
					background_music.nextSong();
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
				// TODO ADD MEDIEVAL GAME MODE SOUNDS
			}
		});

		// their labels

		volumeLabel = new Label("Music", skin);
		soundVolumeLabel = new Label("Sound", skin);

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

		audioTable.add(skipButton).padTop(10).padLeft(40);
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

		// create a new MapStage Object for the variable mapStage

		createMapStage();

		// set number obstacle for initialization to 3
		numberObstacle = 3;

		loadingSound.stop();

		// initialise the gameEndStage
		gameEndStage = new Stage();

		// create botMove

		// botMove = new BotMove();

		// userInput = new UserInputProcessor();

		Gdx.input.setInputProcessor(currentStage);

		botMove = new moveBotTile();

		tileSize = 80;

		updateStagesViewports();

		// ensures game starts in menu
		createMainMenuStage();
	}


	public enum GameState {
		// for determining the current state of the game
		/*
		* The game has 3 states: RED_TURN, GREEN_TURN, NOT_IN_GAME
		* Using these states allows for smooth switching between game assets
		* RED_TURN: the red player has their turn
		* GREEN_TURN: the green player has their turn
		* NOT_IN_GAME: the game is not in progress, in any menuStage
		 */

		RED_TURN, GREEN_TURN, NOT_IN_GAME
	}

	// the first state at game Start is NOT_IN_GAME
	public static GameState currentState = GameState.NOT_IN_GAME;
	
	@Override
	public void render() {
		/*
		* render is called every frame, main-game loop of the game, holds all stages in nested ifs and the processTurn
		 */

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
				if (!isColourChanged) {
					currentMover.addActor(greenMove);
				} else {
					currentMover.addActor(blueMove);
				}
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

		// render the gameEndStage
		gameEndStage.getViewport().apply();
		gameEndStage.act();
		gameEndStage.draw();

		processTurn();

	}

	private void processTurn() {
		/*
		* ProcessTurn is called at end of every Frame and triggers game progression if a Drag&Drop turn is legit /
		* triggers the bot if isBotMatch and RED_MOVE
		 */
		if (currentState == GameState.RED_TURN) {
			if (!isBotMatch){
				if (legitTurn) {
					calculateDamage("red");
					switchTurn(currentState);
					legitTurn = false;
				}
			} else {
				// switch case to make a bot decision for red team
				if (!(botMove.getIsMoving())) {
					switch (botDifficulty) {
						case ("easy"):
							BOT.easyBotMove();
							break;
						case ("medium"):
							BOT.mediumBotMove();
							break;
						case ("hard"):
							BOT.hardBotMove();
							break;
					}
					legitTurn = false;
				} else {
					// add delta float time to BotMove.update
					botMove.update(Gdx.graphics.getDeltaTime()); // updates till moving has finished
				}

				if (botMove.movingFinished) { // if the bot moving has finished, render and attack
					// update the gameBoard officially

					Board.update(botMove.startX, botMove.startY, botMove.endX, botMove.endY);
					switchToStage(createGameStage(isBotMatch));

					calculateDamage("red");
					switchTurn(currentState);

					deathExplosionStage.clear();

					// draws new with switched State and clears the old
					switchToStage(createGameStage(isBotMatch));
				}
			}
		} else if (currentState == GameState.GREEN_TURN) {
			if (legitTurn) {
				calculateDamage("green");
				switchTurn(currentState);
				legitTurn = false;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		/*
		 * fits the needed values when a resize has happened, when a resize has happened
		 */

		// TODO INSTEAD OF WORKING WITH PIXELS IN SCENE2DUI, work with relaitivity of the size of a tile,
		// which is width/24

		mapStage.getViewport().update(width, height, true);
		moveLogoStage.getViewport().update(width, height, true);
		possibleMoveOverlay.getViewport().update(width, height, true);
		currentStage.getViewport().update(width, height, true);
		dottedLineStage.getViewport().update(width, height, true);
		deathExplosionStage.getViewport().update(width, height, true);
		gameEndStage.getViewport().update(width, height, true);

	}

	private void updateStagesViewports() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		mapStage.getViewport().update(width, height, true);
		moveLogoStage.getViewport().update(width, height, true);
		possibleMoveOverlay.getViewport().update(width, height, true);
		currentStage.getViewport().update(width, height, true);
		dottedLineStage.getViewport().update(width, height, true);
		deathExplosionStage.getViewport().update(width, height, true);
		gameEndStage.getViewport().update(width, height, true);
	}

	private void calculateDamage(String teamColor) {
		/*
		* method that goes through each tile of the board and if SoldierTeam is teamColor,
		*  lets it attack the surrounding tiles
		 */
		Soldier[][] gameBoard = Board.getGameBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				Soldier soldier = gameBoard[i][j];
				// for checking if the piece is an artillery -> call checkSurroundingsArtillery
				if (soldier != null && soldier.getTeamColor().equals(teamColor)) {
					Damage.checkSurroundings(i, j);
				}
			}
		}
	}

	private void switchTurn(GameState state) {
		/*
		* switchTurn switches the public GameStage enum between RED and GREEN
		 */
		if (state == GameState.RED_TURN) {
			currentState = GameState.GREEN_TURN;
		} else {
			currentState = GameState.RED_TURN;
		}
	}

	@Override
	public void dispose() {
		/*
		* dispose is used when the game is exited, disposes of all assets
		 */
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
		possibleMoveOverlay.dispose();
		moveLogoStage.dispose();
		shapeRenderer.dispose();
		dottedLineStage.dispose();
		deathExplosionStage.dispose();
		gameEndStage.dispose();

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

		 // dispose of all sound objects
		 smallArmsSound.dispose();
		 bigArmsSound.dispose();
		 dogSound.dispose();
		 helicopterSound.dispose();
		 tankSound.dispose();
		 smallExplosionSound.dispose();
		 bigExplosionSound.dispose();
		 archerSound.dispose();
		 catapultSound.dispose();
		 knightSound.dispose();
		 magicSound.dispose();
		 queenSound.dispose();
		 kingSound.dispose();

		 // dispose of all music
		 background_music.dispose();
		 menu_music.dispose();
		 creditsMusic.dispose();
	}

	public static void switchToStage(Stage newStage) {
		/*
		* this method removes the currentStage and loads a new one
		* used generally in the Stage classes at the end to load the created Stages
		* or combined with a return Stage createStage method
		 */

		if (currentStage != null){
			currentStage.clear();}
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	public static void reRenderGame(){
		/*
		* used to refresh the gameStage if a action has happened that edited the gameBoard.
		 */
		switchToStage(createGameStage(isBotMatch));
	}

	public static void createMainMenuStage() {
		/*
		* method for creating the stage for the main menu
		 */
		switchToStage(MenuStage.initializeUI());
		gameEndStage.clear();
	}

	public static void createHelpStage() {
		/*
		* method for creating the stage for the help display
		 */
		switchToStage(HelpStage.initializeUI());
	}

	public static void createOptionsStage() {
		/*
		* method for creating the stage for the options display
		 */
		switchToStage(OptionsStage.initalizeUI());
	}

	public static void createCreditsStage() {
		/*
		* method for creating the stage for the credits display
		 */
		switchToStage(CreditsStage.initializeUI());
	}

	public static void createGameEndStage (String winnerTeamColour){
		/*
		* method for creating the stage for the game end ON TOP of the gameStage
		* (changing InputProcessor to stop Game Progress)
		 */
		gameEndStage = GameEndStage.initializeUI(winnerTeamColour);
		System.out.println("GameEndStage added");
	}

	public static void createChallengeStage() {
		/*
		* method for creating the stage for the challenge display
		 */
		switchToStage(ChallengeStage.initializeUI());
	}

	public static void createMapStage() {
		/*
		* method for creating the stage for the map that is rendered in variable mapStage
		 */
		mapStage = MapStage.initializeUI();
	}

	public static void setAllowedTiles (ArrayList<Coordinates> validMoveTiles) {
		/*
		* method for setting the ArrayList of allowed tiles to move to and a method for clearing it to nothing
		 */

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
		/*
		* method for turning off the renderOverlay (boolean value changed and not display next render
		 */
		renderOverlay = false;
	}

	public static Coordinates calculateTileByPX(int pxCoordinateX, int pxCoordinateY) {
		/*
		* method for calculating the tile coordinates by pixel coordinates
		 */

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

	public static Coordinates calculatePXbyTile(int tilePositionX, int tilePositionY){
		/*
		* in this first method, the pixel coordinates of the center of a tile is calculated
		* this will be used afterwards to create a dotted Line Animation from an attacker to a defender tile
		 */

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
	public static void addDottedLine(float x1, float y1, float x2, float y2, boolean isDamage){
		/*
		* uses a beginning coordinate and a end coordinate to create an Actor and add it to the LineStage
		 */
		DottedLineActor lineActor = new DottedLineActor(x1, y1, x2, y2, shapeRenderer, isDamage,
				currentState, isColourChanged);
		dottedLineStage.addActor(lineActor);
	}

	// --------------------------------------------------------------------------------------------------------------
	// ------------------------------------------- deathAnimation METHODS -------------------------------------------
	// --------------------------------------------------------------------------------------------------------------
	public static Coordinates calculatePXbyTileNonGDX(int tilePositionX, int tilePositionY){
		/*
		* calculates the most middle pixel of a tile of the chessBoard
		 */
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
		/*
		* adds the DeathAnimation to the deathExplosionStage, adds this action to log
		 */
		DeathExplosionActor deathActor = new DeathExplosionActor(x, y);
		deathExplosionStage.addActor(deathActor);
		System.out.println("Exploded someone at position "+ x + "-" + y);
	}

	public static void addHitMarker(int x, int y){
		/*
		 * adds the DeathAnimation to the deathExplosionStage, adds this action to log
		 */
		HitMarkerActor hitActor = new HitMarkerActor(x, y);
		deathExplosionStage.addActor(hitActor);
		System.out.println("Hit someone at position "+ x + "-" + y);
	}

	// ------------------- methods for setting a to be displayed as empty variable
	public static void resetEmptyCoordinate() {
		useEmpty = false;
	}

	public static void setEmptyCoordinate(int startX, int startY) {
		useEmpty = true;
		emptyX = startX;
		emptyY = startY;
	}
}