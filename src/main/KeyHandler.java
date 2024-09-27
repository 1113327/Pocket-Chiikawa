 package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    Game game;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean interactPressed, enterPressed;

    public KeyHandler(Game ga){
        this.game = ga;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        interactPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //play state
        if(game.gameState == game.playState){
            if(code == KeyEvent.VK_W)
                upPressed = true;
            else if(code == KeyEvent.VK_S)
                downPressed = true;
            else if(code == KeyEvent.VK_A)
                leftPressed = true;
            else if(code == KeyEvent.VK_D)
                rightPressed = true;
            else if(code == KeyEvent.VK_E)
                interactPressed = true;
            else if(code == KeyEvent.VK_ESCAPE)
                game.gameState = game.menuState;
            else if(code == KeyEvent.VK_ENTER)
                enterPressed = true;
            // else if(code == KeyEvent.VK_B) {
            //     game.gameState = game.battleState;
            // }
        }

        //menu state
        else if(game.gameState == game.menuState){
            if(code == KeyEvent.VK_ESCAPE){
                game.gameState = game.playState;
            }
        }

        //dialogue state
        else if(game.gameState == game.dialogueState){
            game.gameState = game.playState;
        }
        else if(game.gameState == game.battleState){
            if(code == KeyEvent.VK_ESCAPE){
                game.gameState = game.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
            upPressed = false;
        else if(code == KeyEvent.VK_S)
            downPressed = false;
        else if(code == KeyEvent.VK_A)
            leftPressed = false;
        else if(code == KeyEvent.VK_D)
            rightPressed = false;
        else if(code == KeyEvent.VK_E)
            interactPressed = false;
    }
}