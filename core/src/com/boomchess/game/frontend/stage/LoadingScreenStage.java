package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.skin;

public class LoadingScreenStage {

    public static Stage initalizeUI() {
        Stage loadingScreen = new Stage();

        // Begin of Options LoadingScreen Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);
        loadingScreen.addActor(root);

        // TODO ADD A RANDOM OUT OF 4 LOADING SCREEN DESIGN

        return loadingScreen;
    }
}
