package com.javarush.task.task27.task2712.kitchen;

import java.util.Observable;
import java.util.Observer;
import com.javarush.task.task27.task2712.ConsoleHelper;


public class Waiter implements Observer {
    @Override
    public void update(Observable cook, Object order) {
        ConsoleHelper.writeMessage(order + " was cooked by " + cook);
    }
}
