package com.boomchess.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Boom Chess");
		config.setWindowedMode(1536, 896);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setResizable(false);
		config.setWindowIcon("logo/Logo3.png");
		new Lwjgl3Application(new BoomChess(), config);
	}
}
