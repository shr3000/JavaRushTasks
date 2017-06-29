package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.*;

import java.io.IOException;
import java.util.*;

public class Order {
    private final int number;
    protected List<Dish> dishes;
    private final Tablet tablet;

    public Order(Tablet tablet) throws IOException{
        this.tablet = tablet;
        number = tablet.getNumber();
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalCookingTime() {
        int totalTime = 0;
        for (Dish d: dishes) {
            totalTime += d.getDuration();
        }
        return totalTime;
    }
    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) return "";
        return String.format("Your order: %s of %s", dishes, tablet);
    }
}
