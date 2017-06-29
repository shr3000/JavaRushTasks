package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    public Tile [][] gameTiles;

    int score;
    int maxTile;

    Stack previousStates = new Stack();
    Stack previousScores = new Stack();
    boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public void autoMove(){
        PriorityQueue queue = new PriorityQueue(4, Collections.reverseOrder());
        queue.add(getMoveEfficiency(this::down));
        queue.add(getMoveEfficiency(this::up));
        queue.add(getMoveEfficiency(this::left));
        queue.add(getMoveEfficiency(this::right));
        MoveEfficiency efficiency = (MoveEfficiency)queue.poll();
        efficiency.getMove().move();
    }

    public boolean hasBoardChanged(){
        int weight = 0;
        int previousWeight = 0;
        Tile [][] previousTiles = (Tile[][])previousStates.peek();
        for (int i = 0; i < gameTiles.length; i++){
            for (int y = 0; y < gameTiles[i].length; y++) {
                weight += gameTiles[i][y].value;
                previousWeight += previousTiles[i][y].value;
            }
        }
        return weight != previousWeight;
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        move.move();
        MoveEfficiency moveEfficiency;
        if (!hasBoardChanged()) moveEfficiency = new MoveEfficiency(-1, 0 , move);
        else moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        rollback();
        return moveEfficiency;
    }

    public void randomMove(){
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0: left();
            case 1: right();
            case 2: up();
            case 3: down();
        }
    }

    private void saveState(Tile[][] tiles) {
        Tile [][] saveGameTiles =  new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++){
            for (int y = 0; y < gameTiles[i].length; y++) {
                Tile tile = new Tile();
                tile.value = gameTiles[i][y].value;
                saveGameTiles[i][y] = tile;
            }
        }
        int saveScore = score;
        previousStates.push(saveGameTiles);
        previousScores.push(saveScore);
        isSaveNeeded = false;
    }

    public void rollback(){
        if (!previousStates.isEmpty() & !previousScores.isEmpty()) {
            gameTiles = (Tile[][]) previousStates.pop();
            score = (int) previousScores.pop();
        }
    }

    public boolean canMove(){
        if (!getEmptyTiles().isEmpty()) return true;
        for (int i = 0; i < gameTiles.length; i++) {
            for (int y = 0; y < gameTiles[i].length; y++) {
                if (gameTiles[i][y].value >  0 && y < gameTiles[i].length - 1) {
                    if (gameTiles[i][y].value == gameTiles[i][y+1].value) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < gameTiles.length; i++) {
            for (int y = 0; y < gameTiles[i].length; y++) {
                if (gameTiles[y][i].value >  0 && y < gameTiles[y].length - 1) {
                    if (gameTiles[y][i].value == gameTiles[y+1][i].value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void rotate90() {
        Tile [][] tiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int y = 0; y < gameTiles[i].length;y++) {
                Tile tile = new Tile();
                tile.value = gameTiles[i][y].value;
                tiles[y][gameTiles.length - 1 - i] = tile;
            }
        }
        gameTiles = tiles;
    }

    public void right() {
        saveState(gameTiles);
        rotate90();
        rotate90();
        left();
        rotate90();
        rotate90();
    }

    public void up(){
        saveState(gameTiles);
        rotate90();
        rotate90();
        rotate90();
        left();
        rotate90();
    }

    public void down() {
        saveState(gameTiles);
        rotate90();
        left();
        rotate90();
        rotate90();
        rotate90();
    }

    public void left() {
        if (isSaveNeeded) saveState(gameTiles);
        boolean isChange = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isChange = true;
        }
        if (isChange) addTile();
        isSaveNeeded = true;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isChange = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value == 0 && i < tiles.length - 1) {
                for (int y = i+1; y < tiles.length; y++){
                    if (tiles[y].value != 0) {
                        tiles[i].value = tiles[y].value;
                        tiles[y].value = 0;
                        isChange = true;
                        break;
                    }
                }
            }
        }
        return isChange;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isChange = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value >  0 && i < tiles.length - 1) {
                if (tiles[i].value == tiles[i+1].value) {
                    int summ = tiles[i].value * 2;
                    tiles[i].value = summ;
                    tiles[i+1].value = 0;
                    if (summ > maxTile) maxTile = summ;
                    score += summ;
                    isChange = true;
                }
            }
        }
        compressTiles(tiles);
        return isChange;
    }

    public  void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++){
            for (int y = 0; y < gameTiles[i].length; y++) {
                gameTiles[i][y] = new Tile();
            }
        }
        addTile();
        addTile();
        score = 0;
        maxTile = 2;
    }

    private void addTile() {
        if (getEmptyTiles().isEmpty()) return;
        Tile tile = getEmptyTiles().get((int)(getEmptyTiles().size() * Math.random()));
        tile.value = Math.random() < 0.9 ? 2 : 4;
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++){
            for (int y = 0; y < gameTiles[i].length; y++) {
                if (gameTiles[i][y].isEmpty()) list.add(gameTiles[i][y]);
            }
        }
        return list;
    }
}
