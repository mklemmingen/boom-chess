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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class BoomChess extends ApplicationAdapter {

	// used for essential resolution and drawing matters
	private SpriteBatch batch;
	private OrthographicCamera camera;
	// loading of essential background images
	private Texture background;
	// start of asset loading red team
	private Texture general_red_left;
	private Texture infantry_red_left;
	private Texture tank_red_left;
	private Texture helicopter_red_left;
	private Texture artillery_red_left;
	private Texture wardogs_red_left;
	// start of asset loading red team
	private Texture general_green_right;
	private Texture infantry_green_right;
	private Texture tank_green_right;
	private Texture helicopter_green_right;
	private Texture artillery_green_right;
	private Texture wardogs_green_right;
	// start of asset loading Sound and Music
	private Sound boom;
	private Music background_music;
	private Music menu_music;
	// loading of the menu images
	private Texture menu;
	private Texture options;
	private Texture credits;
	private Texture exit;
	private Texture help;
	// usage sor main menu skin and stages
	private Skin skin;
	private Stage menuStage;
	private Stage optionsStage;
	private Stage creditsStage;
	private Stage exitStage;
	private Stage helpStage;
	// usage for the buttons
	private TextButton play2Button;
	private TextButton playBotButton;
	private TextButton optionsButton;
	private TextButton creditsButton;
	private TextButton exitButton;
	private TextButton helpButton;
	// boolean variables for checking if the game is in the menu or not
	private final boolean in_menu = true;

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


		// start menu music, and then the play of the background music if menu has been passed
		menu_music.setLooping(true);
		if (in_menu) {
			menu_music.setLooping(true);
			menu_music.play();
		}
		else {
			menu_music.stop();
			background_music.setLooping(true);
			background_music.play();
		}

		// for the tiled map used as the chess board

		tiledMap = new TmxMapLoader().load("map/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		skin = new Skin(Gdx.files.internal("menu.commodore64/uiskin.json"));

		menuStage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(menuStage);

		// Begin of Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
		Table root = new Table();
		root.setFillParent(true);
		menuStage.addActor(root);

		Image title = new Image(new Texture("BoomLogo.png"));
		root.add(title).top().padBottom(20);
		root.row();

		TextButton helpButton = new TextButton("Help!", skin);
		root.add(helpButton).padBottom(20);
		root.row();

		TextButton play2Button = new TextButton("Play a 2 Player Game", skin);
		root.add(play2Button).padBottom(20);
		root.row();

		TextButton playBotButton = new TextButton("Play a Game Against the Computer", skin);
		root.add(playBotButton).padBottom(20);
		root.row();

		TextButton optionsButton = new TextButton("Options", skin);
		root.add(optionsButton).padBottom(20);
		root.row();

		TextButton creditsButton = new TextButton("Credits", skin);
		root.add(creditsButton).padBottom(20);
		root.row();

		TextButton exitButton = new TextButton("Exit", skin);
		root.add(exitButton).padBottom(20);
		root.row();

		// End of first menu-layer Layout


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		menuStage.act();
		menuStage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
