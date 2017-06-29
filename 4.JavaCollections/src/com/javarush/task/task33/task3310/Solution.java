package com.javarush.task.task33.task3310;


import com.javarush.task.task33.task3310.strategy.FileStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.OurHashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> result = new HashSet<>();
        for(String s : strings){
            result.add(shortener.getId(s));
        }
        return result;
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> result = new HashSet<>();
        for(Long l : keys){
            result.add(shortener.getString(l));
        }
        return result;
    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        for (long i = 0; i < elementsNumber; i++) {
            strings.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date start1 = new Date();
        Set<Long> ids = getIds(shortener, strings);
        Date end1 = new Date();
        Helper.printMessage(String.valueOf(end1.getTime() - start1.getTime()));
        Date start2 = new Date();
        Set<String> stringsTest = getStrings(shortener, ids);
        Date end2 = new Date();
        Helper.printMessage(String.valueOf(end2.getTime() - start2.getTime()));
        if (strings.size() == stringsTest.size()) Helper.printMessage("Тест пройден.");
        else Helper.printMessage("Тест не пройден.");
    }

    public static void main(String[] args) {

        StorageStrategy strategy = new HashMapStorageStrategy();
        testStrategy(strategy, 10);

        StorageStrategy strategy1 = new OurHashMapStorageStrategy();
        testStrategy(strategy1, 10);

        StorageStrategy strategy2 = new FileStorageStrategy();
        testStrategy(strategy2, 10);
    }

}