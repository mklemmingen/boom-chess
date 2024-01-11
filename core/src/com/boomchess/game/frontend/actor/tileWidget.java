package com.boomchess.game.frontend.actor;

import static com.boomchess.game.BoomChess.batch;
import static com.boomchess.game.BoomChess.calculatePXbyTile;
import static com.boomchess.game.BoomChess.calculateTileByPX;
import static com.boomchess.game.BoomChess.calculateTileByPXNonGDX;
import static com.boomchess.game.BoomChess.clearAllowedTiles;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.damageNumberStage;
import static com.boomchess.game.BoomChess.empty;
import static com.boomchess.game.BoomChess.emptyX;
import static com.boomchess.game.BoomChess.emptyY;
import static com.boomchess.game.BoomChess.isBotMatch;
import static com.boomchess.game.BoomChess.isColourChanged;
import static com.boomchess.game.BoomChess.legitTurn;
import static com.boomchess.game.BoomChess.setAllowedTiles;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.useEmpty;
import static com.boomchess.game.BoomChess.wrongMoveStage;
import static com.boomchess.game.frontend.stage.GameStage.showHealth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.Hill;
import com.boomchess.game.frontend.animations.soldierAnimation;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.util.ArrayList;

public class tileWidget extends Actor {

    // a tileWidget is a widget that represents a tile on the board.
    // It has a position of x and y on the chess board and a boolean that determines
    // if it is visible or not

    private int x;
    private int y;

    private Coordinates coords;

    // stack that all the widgets are added to ---- THE MAIN ONE
    private final Stack root = new Stack();

    // texture for the standard no animation (is Image)
    private final Image standardNoAnimation;

    // animation for the soldier
    private final soldierAnimation widgetSoldierAnimation;

    // Stack of black Circle and health Number
    // can be turned on and off via visibility
    private final Stack healthStack = new Stack();

    // Image of the attack Radius
    // can be turned on and off via visibility
    private final Image attackRadius;

    // Stack for the damage-interval
    // can be turned on and off via visibility
    private final Stack damageIntervalStack = new Stack();

    private final Soldier soldier;

    public tileWidget(int x, int y){

        this.x = x;
        this.y = y;
        this.coords = new Coordinates();
        coords.setCoordinates(x, y);

        // visible generally right off at the start
        this.setVisible(true);

        batch.begin();

        Soldier[][] gameBoard = Board.getGameBoard();
        soldier = gameBoard[x][y];



        // if the current coordinate is the empty Variable coordinates and its
        // useEmpty = true, the solPiece has an Image of Empty, if not continue to rest

        // if BoomChess.isAnimated, make solPiece a soldierAnimation Object and if not, an Image Object
        if (BoomChess.isAnimated) {
            if (soldier instanceof Hill){
                standardNoAnimation = new Image(((takeSelfieInterface) soldier).takeSelfie());
            }else {
                if(BoomChess.isMedievalMode){
                    // standard image load
                    standardNoAnimation = new Image(((takeSelfieInterface) soldier).takeSelfie());
                }
                else {
                    // switch statement for deciding which
                    // soldier animation to take, each case has a green or red and a
                    // iscolourreversed if statement between green and blue
                    switch (soldier.getPieceType()) {

                        case "general":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueGeneralAnimation =
                                            new soldierAnimation(soldier);
                                } else {
                                    soldierAnimation greenGeneralAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redGeneralAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                        case "tank":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueTankAnimation =
                                            new soldierAnimation(soldier);
                                } else {
                                    soldierAnimation greenTankAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redTankAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                        case "artillery":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueArtilleryAnimation =
                                            new soldierAnimation(soldier);
                                    solPiece.add(blueArtilleryAnimation);
                                } else {
                                    soldierAnimation greenArtilleryAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redArtilleryAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                        case "infantry":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueInfantryAnimation =
                                            new soldierAnimation(soldier);
                                } else {
                                    soldierAnimation greenInfantryAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redInfantryAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                        case "helicopter":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueHelicopterAnimation =
                                            new soldierAnimation(soldier);
                                } else {
                                    soldierAnimation greenHelicopterAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redHelicopterAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                        case "wardog":
                            if (soldier.getTeamColor().equals("green")) {
                                if (isColourChanged) {
                                    soldierAnimation blueWardogAnimation =
                                            new soldierAnimation(soldier);
                                } else {
                                    soldierAnimation greenWardogAnimation =
                                            new soldierAnimation(soldier);
                                }
                            } else {
                                soldierAnimation redWardogAnimation =
                                        new soldierAnimation(soldier);
                            }
                            break;

                    }
                }
            }
        } else {
            // load the corresponding image through the Soldier Take Selfie Method
            if (soldier instanceof takeSelfieInterface) {
                Image standardNoAnimation = new Image(((takeSelfieInterface) soldier).takeSelfie());
            }
        }

        standardNoAnimation.setSize(tileSize, tileSize);
        widgetSoldierAnimation.setSize(tileSize, tileSize);

        root.add(standardNoAnimation);
        root.add(widgetSoldierAnimation);

        // health ------------------------------------------------------------------------------

        healthStack = new Stack();

        // try getHealth, is null, make health to -1
        int health;
        if (!(soldier == null)) {
            health = soldier.getHealth();
            // use the 'soldier' object since it's the same as gameBoard[X][Y]
        } else {
            health = -1;
        }

        if (health < 15 && health > 0) {
            // Apply a light red hue effect to the tileWidget's image
            Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
            solPiece.setColor(lightRed);
        }

        tileWidget.add(solPiece);

        if (!(health == -1)) {
            // tileWidget is only move able if a Piece is on it, meaning it has health
            tileWidget.setTouchable(Touchable.enabled);
            // draw the health number if showHealth is true
            if(showHealth) {
                Image blackCircle = new Image(BoomChess.blackCircle);
                blackCircle.setSize(tileSize, tileSize);
                tileWidget.add(blackCircle);
                Container<Table> healthContainer =
                        HealthNumber.giveContainer(soldier.getStandardHealth(), health);
                tileWidget.add(healthContainer);
            }
        }

        // if the current soldier is instance of artillery, add the attack radius
        // of 5to5, if not, 3to3. safe it in a container and place its middle on
        // on the middle of the tileWidget

        // load both attack radius (3to3 and 5to5) images
        Image attackRadius3to3 = new Image(BoomChess.threeTOthreeCircle);
        attackRadius3to3.setSize(tileSize*3, tileSize*3);
        Image attackRadius5to5 = new Image(BoomChess.fiveTOfiveCircle);
        attackRadius5to5.setSize(tileSize*5, tileSize*5);

        // create container for the attack radius
        final Stack attackRadiusContainer = new Stack();

        // set the middle of the attack radius container to the middle of the tileWidget
        Coordinates coord = BoomChess.calculatePXbyTile(x, y);

        if(soldier instanceof Artillery){
            attackRadiusContainer.add(attackRadius5to5);
            attackRadiusContainer.setSize(tileSize*5, tileSize*5);
        } else {
            attackRadiusContainer.add(attackRadius3to3);
            attackRadiusContainer.setSize(tileSize*3, tileSize*3);
        }

        attackRadiusContainer.setPosition(
                coord.getX()-attackRadiusContainer.getWidth()/2,
                coord.getY()-attackRadiusContainer.getHeight()/2);

        attackRadiusContainer.setVisible(false);

        attackRadiusContainer.toFront();

        // add a damageInterval if showPossibleDamage is true
        if(BoomChess.showIntervals){
            // takeSelfieInterface with showInterval method
            if(soldier instanceof takeIntervalSelfie){
                // Create an intermediary container (Table or Stack) for layout purposes
                Table intervalContainer = new Table();

                // Create the damageInterval image
                Image damageInterval = new Image(((takeIntervalSelfie) soldier).showInterval());
                damageInterval.setFillParent(false);

                // Set the size of the damageInterval relative to the tileSize
                damageInterval.setSize(tileSize/4, tileSize/8);

                intervalContainer.add(damageInterval);

                // Add the intervalContainer to the tileWidget Stack
                // This ensures that damageInterval maintains its size and position
                // within intervalContainer
                tileWidget.add(intervalContainer);

                intervalContainer.setSize(tileWidget.getWidth(), tileWidget.getHeight());
            }
        }

        // add a Listener only if (!isBotMatch) || (isBotMatch && (state == GameState.GREEN_TURN))
        // since we do not want Red to have Drag if it's a bot-match, since that's the bot team
        if ((!isBotMatch) || (isBotMatch && (currentState == BoomChess.GameState.GREEN_TURN))) {
            final int finalI = x;
            final int finalJ = y;

            tileWidget.addListener(new DragListener() {
                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {
                    // Code runs when dragging starts:

                    // Get the team color of the current tile
                    Soldier[][] gameBoard = Board.getGameBoard();
                    String tileTeamColor = gameBoard[finalI][finalJ].getTeamColor();

                    // If it's not the current team's turn, cancel the drag and return
                    if ((currentState == BoomChess.GameState.RED_TURN && !tileTeamColor.equals("red")) ||
                            (currentState == BoomChess.GameState.GREEN_TURN && !tileTeamColor.equals("green"))) {
                        event.cancel();

                        // adds a logo on screen that says that the movement was not allowed yet
                        WrongMoveIndicator indi = new WrongMoveIndicator();
                        wrongMoveStage.addActor(indi);
                        //plays a brick sound
                        indi.makeSound();

                        BoomChess.reRenderGame();
                        return;
                    }

                    if(BoomChess.showAttackCircle){
                        // attack radius
                        attackRadiusContainer.setVisible(true);
                    }

                    tileWidget.toFront(); // Bring the actor to the front, so it appears above other actors
                    // as long as the mouse is pressed down, the actor is moved to the mouse position
                    // we calculate the tiles it can move to and highlight these tiles with a slightly red hue
                    // the calculated tiles are part of a ArrayList variable that is created at create
                    // of the whole programm
                    // and gets cleared once we touchDragged the actor to a new position

                    // switch statement for deciding which
                    // Chess Pieces Class mathMove is used to assign the ArrayList validMoveTiles

                    assert soldier != null;
                    setAllowedTiles(soldier.mathMove(finalI, finalJ));
                }

                @Override
                public void drag(InputEvent event, final float x, final float y, int pointer) {

                    // Code here will run during the dragging
                    tileWidget.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);
                    attackRadiusContainer.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);

                    // if the user has showPossibleDamage activated, show the possible damage
                    // of all attackable enemies
                    // from the current tile the drag is hovering over
                    Gdx.app.log("DragMethod", "About to post runnable");
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {

                            Gdx.app.log("Runnable", "Inside the runnable");

                            // for thread safety in OpenGL, which libGDX used, we
                            // add a runnable for stage manipulation in the ereignis-thread
                            // to main thread

                            // Modify your UI components here
                            damageNumberStage.clear();

                            if(BoomChess.showPossibleDamage) {

                                // we determine if the current tileWidget is of the soldier
                                // artillery or not

                                int distance = 1;
                                if(soldier instanceof Artillery){
                                    distance = 2;
                                }

                                // go through the gameboard starting at the current tile and
                                // check each tile nearby if there is an enemy soldier

                                Soldier[][] gameBoard = Board.getGameBoard();

                                // current calculation position from local
                                // to screen coordinates

                                Vector2 screenCoords =
                                        tileWidget.localToScreenCoordinates(new Vector2(x, y));
                                Coordinates currentCords =
                                        calculateTileByPXNonGDX((int) screenCoords.x, (int) screenCoords.y);

                                int currentX = currentCords.getX();
                                int currentY = currentCords.getY();

                                int startX = Math.max(0, currentX - distance);
                                int endX = Math.min(8, currentX + distance);

                                int startY = Math.max(0, currentY - distance);
                                int endY = Math.min(7, currentY + distance);

                                // get the color of the soldier that is currently being dragged
                                String attackColor = gameBoard[finalI][finalJ].getTeamColor();

                                // get the soldier type that the currently dragged soldier
                                // can inflict special damage to

                                int boniValue = 0;
                                int malusValue = 0;
                                Class<? extends Soldier> boniGI = null;
                                Class<? extends Soldier> malusGI = null;

                                Soldier boniSoldier = BoomChess.getSpecialBoniSoldier(soldier);
                                if(boniSoldier != null){
                                    boniGI = boniSoldier.getClass();
                                    boniValue = BoomChess.getSpecialBoniValue(soldier);
                                }

                                Soldier malusSoldier = BoomChess.getSpecialMalusSoldier(soldier);
                                if(malusSoldier != null){
                                    malusGI = malusSoldier.getClass();
                                    malusValue = BoomChess.getSpecialMalusValue(soldier);
                                }



                                for (int i = startX; i <= endX; i++) {
                                    for (int j = startY; j <= endY; j++) {
                                        if (i == finalI && j == finalJ) continue;

                                        Soldier currentSoldier = gameBoard[i][j];

                                        if (currentSoldier != null &&
                                                !(currentSoldier instanceof Empty) &&
                                                !(currentSoldier instanceof Hill)) {

                                            String hurtColor = currentSoldier.getTeamColor();
                                            if (!hurtColor.equals(attackColor)) {

                                                Gdx.app.log("Debug", "Checking tile [" + i + "," + j + "], " +
                                                        "Enemy Color: " + hurtColor + ", Attacker Color: " + attackColor +
                                                        ", BoniGI: " + boniGI + ", MalusGI: " + malusGI);

                                                if (boniSoldier != null &&
                                                        boniGI.isInstance(currentSoldier)) {
                                                    damageNumberStage.addActor(
                                                            new SpecialDamageIndicator(boniValue, i, j, true));
                                                    Gdx.app.log("Runnable", "Added Boni");
                                                }

                                                if (malusSoldier != null &&
                                                        malusGI.isInstance(currentSoldier)) {
                                                    damageNumberStage.addActor(
                                                            new SpecialDamageIndicator(malusValue, i, j, false));
                                                    Gdx.app.log("Runnable", "Added Malus");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {

                    damageNumberStage.dispose();
                    damageNumberStage = new Stage();

                    // Code here will run when the player lets go of the actor

                    // Get the position of the tileWidget relative to the parent actor (the gameBoard)
                    Vector2 localCoords = new Vector2(x, y);
                    // Convert the position to stage (screen) coordinates
                    Vector2 screenCoords = tileWidget.localToStageCoordinates(localCoords);


                    Coordinates currentCoord = calculateTileByPX((int) screenCoords.x, (int) screenCoords.y);

                    // for loop through validMoveTiles, at each tile we check for equality of currentCoord
                    // with the Coordinate
                    // in the ArrayList by using currentCoord.checkEqual(validMoveTiles[i]) and if true,
                    // we set the
                    // validMove Variable to true, call on the update method of the Board class and break
                    // the for loop
                    // then clear the Board.


                    ArrayList<Coordinates> validMoveTiles = Board.getValidMoveTiles();
                    for (Coordinates validMoveTile : validMoveTiles) {
                        if (currentCoord.checkEqual(validMoveTile)) {
                            // Board.update with oldX, oldY, newX, newY
                            Board.update(finalI, finalJ, currentCoord.getX(), currentCoord.getY());
                            legitTurn = true;
                            break;
                        }
                    }

                    // and the validMoveTiles are cleared
                    clearAllowedTiles(); // for turning off the Overlay
                    Board.emptyValidMoveTiles();

                    attackRadiusContainer.setVisible(false);

                    BoomChess.reRenderGame();

                }
            });
        }
        gameStage.addActor(attackRadiusContainer);
        root.add(tileWidget).size(tileSize);

        // -------------------- visibility ------------------------------------------------------

        // at the end of the tileWidget creation, we check the visbility of the just
        // created containers, Stack, textures, by using the booleans of BoomChess

        // if showHealth is false, we set the healthStack to invisible
        if(!showHealth){
            healthStack.setVisible(false);
        }

        // if showAttackCircle is false, we set the attackRadius to invisible
        if(!BoomChess.showAttackCircle){
            attackRadiusContainer.setVisible(false);
        }

        // if showPossibleDamage is false, we set the damageIntervalStack to invisible
        if(!BoomChess.showPossibleDamage){
            damageIntervalStack.setVisible(false);
        }

        // --------------------------------------------------------------------------------------

        batch.end();
    }

    // --------------- necessary tools

    // draw
    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        root.draw(batch, parentAlpha);
    }

    // act
    @Override
    public void act(float delta) {
        root.act(delta);
    }

    public void death(){
        this.setVisible(false);
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y=y;
    }

    public int getBoardX(){
        return x;
    }

    public int getBoardY(){
        return y;
    }

    public void setVisibility(boolean visible){
        this.setVisible(visible);
    }

    public boolean getVisibility(){
        return this.isVisible();
    }

    public Image getStandardNoAnimation(){
        return standardNoAnimation;
    }

    public void moveToScreenLocation(float x, float y){
        this.setPosition(x, y);
    }

    public boolean equalLocationTo(Coordinates paraCoords){
        return paraCoords.checkEqual(coords);
    }

    public void moveToBoardLocation(int x, int y){
        this.x = x;
        this.y = y;
        Coordinates coords = calculatePXbyTile(x, y);
        moveToScreenLocation(coords.getX(), coords.getY());
    }
}
