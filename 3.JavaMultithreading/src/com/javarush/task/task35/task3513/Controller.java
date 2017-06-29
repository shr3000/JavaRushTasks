package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter{
    private Model model;
    private View view;
    private static final int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    public View getView() {
        return view;
    }

    public Tile [][] getGameTiles(){
        return model.getGameTiles();
    }

    public int getScore(){
        return model.score;
    }

    public void resetGame(){
        model.score = 0;
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) resetGame();
        if (!model.canMove()) view.isGameLost = true;
        if (!view.isGameWon && !view.isGameLost)
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT  : {model.left(); break;}
                case KeyEvent.VK_RIGHT : {model.right(); break;}
                case KeyEvent.VK_UP: {model.up(); break;}
                case KeyEvent.VK_DOWN  : {model.down(); break;}
            }
        if (model.maxTile == WINNING_TILE) view.isGameWon = true;
        if (e.getKeyCode() == KeyEvent.VK_Z) model.rollback();
        if (e.getKeyCode() == KeyEvent.VK_R) model.randomMove();
        if (e.getKeyCode() == KeyEvent.VK_A) model.autoMove();
        view.repaint();
    }
}
