package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.*;
import com.javarush.task.task27.task2712.kitchen.*;

import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
    }
    public void register(Cook cook) {
        cooks.add(cook);
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public TreeMap<Date, Long> countAllProfit () {
        TreeMap<Date, Long> map = new TreeMap<>(Collections.<Date>reverseOrder());
        for (EventDataRow eventDataRow: statisticStorage.getData(EventType.SELECTED_VIDEOS)) {
            Date date = dateToMidnight(eventDataRow.getDate());
            VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) eventDataRow;
            if (map.containsKey(date)) {
                map.put(date, map.get(date) + videoSelectedEventDataRow.getAmount());
            } else map.put(date, videoSelectedEventDataRow.getAmount());
        }
        return map;
    }

    public Date dateToMidnight(Date date)
    {
        GregorianCalendar roundedDate = new GregorianCalendar();
        roundedDate.setTime(date);
        roundedDate.set(Calendar.HOUR_OF_DAY, 0);
        roundedDate.set(Calendar.MINUTE, 0);
        roundedDate.set(Calendar.SECOND, 0);
        roundedDate.set(Calendar.MILLISECOND, 0);
        return roundedDate.getTime();
    }

    public Map<Date, TreeMap<String, Integer>> countCookWork () {
        Map<Date, TreeMap<String, Integer>> cooksWork = new TreeMap<>(Collections.<Date>reverseOrder());
        for (EventDataRow eventDataRow: statisticStorage.getData(EventType.COOKED_ORDER)) {
            Date date = dateToMidnight(eventDataRow.getDate());
            CookedOrderEventDataRow eventData = (CookedOrderEventDataRow) eventDataRow;
            int time = eventData.getTime();
//            if (time == 0) continue;
//            if (time % 60 == 0) time = time / 60;
//            else time = time / 60 + 1;
            if (cooksWork.containsKey(date)) {
                if (cooksWork.get(date).containsKey(eventData.getCookName())) {
                    cooksWork.get(date).put(eventData.getCookName(), cooksWork.get(date).get(eventData.getCookName()) +
                            time);
//                            eventData.getTime());
//                            (int) Math.ceil(eventData.getTime()/60));
                } else {
                    cooksWork.get(date).put(eventData.getCookName(), time);
//                    cooksWork.get(date).put(eventData.getCookName(), eventData.getTime());
//                    cooksWork.get(date).put(eventData.getCookName(), (int) Math.ceil(eventData.getTime()/60));
                }
            } else {
                TreeMap<String, Integer> cookInfo = new TreeMap<>();
                cookInfo.put(eventData.getCookName(), time);
//                cookInfo.put(eventData.getCookName(), eventData.getTime());
//                cookInfo.put(eventData.getCookName(), (int) Math.ceil(eventData.getTime()/60));
                cooksWork.put(date, cookInfo);
            }
        }
        return cooksWork;
    }

    private class StatisticStorage {                               //STORAGE!!!!!!!!!!!!!!!!!1
        private Map<EventType, List<EventDataRow>> storage;
        private StatisticStorage() {
            this.storage = new HashMap<>();
            for (EventType e: EventType.values()) {
                storage.put(e, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private List<EventDataRow> getData (EventType type) {
            return storage.get(type);
        }
    }
}
