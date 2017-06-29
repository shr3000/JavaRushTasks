package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.*;
import com.javarush.task.task27.task2712.statistic.event.*;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer{
    private String name;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable tablet, Object order) {
        Order order1 = (Order) order;
        ConsoleHelper.writeMessage("Start cooking - " + order1 + ", cooking time " + order1.getTotalCookingTime() + "min");
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(tablet.toString(),
                name, order1.getTotalCookingTime()*60, order1.getDishes()));
        setChanged();
        notifyObservers(order);
    }

    @Override
    public String toString() {
        return name;
    }
}
