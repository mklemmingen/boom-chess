package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;


public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters
	private SpriteBatch batch;
	private OrthographicCamera camera;
	// loading of essential background images
	private Texture background;
	// start of asset loading Sound and Music
	public Sound boom;
	public Music background_music;
	public Music menu_music;
	// usage sor main menu skin and stages
	private Skin skin;
	private Stage currentStage;

	// for the tiled map
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");

		// creation of the batch for drawing the images
		batch = new SpriteBatch();

		// creation of the camera fitting to the set resolution in DesktopLauncher

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1536, 896);


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
		* and the usage of the buttons/ui-elements/so called actors and childactors to be used
		* stages will be the the way we display all menus and the game itself
		*/

		// skin (look) of the buttons via a prearranged json file
		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));


		currentStage = createMainMenuStage();
	}

	@Override
	public void render () {
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
	public void dispose () {
		batch.dispose();
		skin.dispose();
		currentStage.dispose();
	}

	private void switchToStage(Stage newStage) {
		currentStage.dispose();
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}

	private Stage createMainMenuStage() {

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

	private Stage createHelpStage() {
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

	private Stage createOptionsStage() {
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

	private Stage createCreditsStage() {
		Stage creditsStage = new Stage();

		// Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		creditsStage.addActor(root);

		// TODO add long list of names of people who worked on the game, what they did, sources and tools used

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

	private Stage createGameStage(boolean isBotMatch) {
		Stage gameStage = new Stage();

		// start of asset loading red team
		Texture general_red_left;
		Texture infantry_red_left;
		Texture tank_red_left;
		Texture helicopter_red_left;
		Texture artillery_red_left;
		Texture wardogs_red_left;
		// start of asset loading red team
		Texture general_green_right;
		Texture infantry_green_right;
		Texture tank_green_right;
		Texture helicopter_green_right;
		Texture artillery_green_right;
		Texture wardogs_green_right;

		// stop menu music and start background_music
		menu_music.stop();
		background_music.setLooping(true);
		background_music.play();

		// Begin of GameLayout - Root Table arranges content automatically and adaptively as ui-structure
		final Table root = new Table();
		root.setFillParent(true);
		gameStage.addActor(root);

		// TODO try to implement the game board as a tiled map and the pieces as actors on top of it
		//  combine the tiled map renderer with the stage renderer? Research: addressing individual .tmx tiles in code
		//  - corresponding to the 2D Array Game Board, the pieces on it, their stats as clean health bars.
		//  ----------------------------------------------------------------------------------------------
		//  Actor-Images must be 64x64px. Add Exit-Button at the Bottom right corner of the screen
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

		// Exit to Main Menu button to return to the main menu
		TextButton menuButton = new TextButton("Return to Main Menu", skin);
		root.add(menuButton).padBottom(20);
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchToStage(createMainMenuStage());
			}
		});
		root.row();

		return gameStage;
	}

}

