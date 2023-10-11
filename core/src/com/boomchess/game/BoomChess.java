package com.boomchess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BoomChess extends ApplicationAdapter {
	// used for essential resolution and drawing matters
	private SpriteBatch batch;
	private OrthographicCamera camera;
	// loading of essential background images
	private Texture logo;
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
	private Music retro_wave;

	@Override
	public void create () {
		batch = new SpriteBatch();
		logo = new Texture("Logo_BoomChess.jpg");

		// load the drop sound effect and the rain background "music"
		boom = Gdx.audio.newSound(Gdx.files.internal("boom.ogg"));
		retro_wave = Gdx.audio.newMusic(Gdx.files.internal("retro-wave.ogg"));

		// start the playback of the background music immediately
		retro_wave.setLooping(true);
		retro_wave.play();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(logo, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		logo.dispose();
	}
}
