package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DirectorTablet {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdvertisementProfit(){
        long totalAmount = 0;

        TreeMap<Date, Long> map = StatisticManager.getInstance().countAllProfit();
        if (map.isEmpty()) return;
        for (Map.Entry<Date, Long> pair : map.entrySet()) {
            totalAmount += pair.getValue();
            String s = simpleDateFormat.format(pair.getKey());
            ConsoleHelper.writeMessage(String.format("%s - %.2f", s, 0.01f*pair.getValue()));
        }
        ConsoleHelper.writeMessage(String.format("Total - %.2f", 0.01f*totalAmount));
//        ConsoleHelper.writeMessage("");
    }
    public void printCookWorkloading(){
        Map<Date, TreeMap<String, Integer>> map = StatisticManager.getInstance().countCookWork();
        if(map.isEmpty()) return;
        for (Map.Entry<Date, TreeMap<String, Integer>> pair: map.entrySet()) {
            String s = simpleDateFormat.format(pair.getKey());
            ConsoleHelper.writeMessage(s);
            for (Map.Entry<String, Integer> p: pair.getValue().entrySet()){
                int time = p.getValue();
                if (time == 0) continue;
                if (time % 60 == 0) time = time / 60;
                else time = time / 60 + 1;

//                ConsoleHelper.writeMessage(String.format("%s - %d min", p.getKey(), p.getValue()));
                ConsoleHelper.writeMessage(String.format("%s - %d min", p.getKey(), time));
//                ConsoleHelper.writeMessage(String.format("%s - %d min", p.getKey(), (int) Math.ceil(p.getValue()/60)));
            }
//            ConsoleHelper.writeMessage("");
        }

    }
    public void printActiveVideoSet(){

    }
    public void printArchivedVideoSet(){}
}
