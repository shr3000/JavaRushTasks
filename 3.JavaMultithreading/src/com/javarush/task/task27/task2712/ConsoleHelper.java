package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static String readString() throws IOException {
        return reader.readLine();
    }
    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> list = new ArrayList<>();
        writeMessage("Выберите блюдо - " + Dish.allDishesToString());
        while (true) {
            String dish = readString();
            if (dish.toUpperCase().equals("EXIT")) break;
            try {
                list.add(Dish.valueOf(dish));
            } catch (IllegalArgumentException e) {
                writeMessage(dish + " is not detected");
            }
        }
        return list;
    }
}
