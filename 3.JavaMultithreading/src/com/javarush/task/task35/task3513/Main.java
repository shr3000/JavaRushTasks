package com.javarush.task.task35.task3513;

import javax.swing.*;

/**
 * Created by MaX on 10.03.2017.
 */
public class Main {
    public static void main(String[] args) {


        Model model = new Model();
        Controller controller = new Controller(model);
        JFrame game = new JFrame();
        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450, 500);
        game.setResizable(false);

        game.add(controller.getView());


        game.setLocationRelativeTo(null);
        game.setVisible(true);
//        for (int i = 0; i < model.gameTiles.length; i++){
//            for (int y = 0; y < model.gameTiles[i].length; y++) {
//                System.out.print(model.gameTiles[i][y].value);
//            }
//            System.out.println();
//        }
//        model.rotate90();
//        for (int i = 0; i < model.gameTiles.length; i++){
//            for (int y = 0; y < model.gameTiles[i].length; y++) {
//                System.out.print(model.gameTiles[i][y].value);
//            }
//            System.out.println();
//        }

    }
}
