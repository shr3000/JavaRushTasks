package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.*;
import com.javarush.task.task27.task2712.statistic.event.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    private List<List> findAllVar(List<Advertisement> list, List<List> all,  List<Advertisement> var, boolean timeIsUp) {
        if (list.isEmpty()) {
            if (!var.isEmpty() && !timeIsUp) {
                all.add(var);
            }
            return all;
        }
        int timeCook = timeSeconds;
        if (!var.isEmpty()) {
            for (Advertisement a: var) timeCook -= a.getDuration();
        }
        List<Advertisement> var1 = new ArrayList<>();
        var1.addAll(var);

        boolean timeIsUp1 = timeIsUp;

        if (list.get(0).getDuration() <= timeCook) var1.add(list.get(0));
        else timeIsUp1 = true;

        list.remove(0);
        List<Advertisement> list1 = new ArrayList<>();
        list1.addAll(list);

        findAllVar(list1, all, var1, timeIsUp1);
        findAllVar(list, all, var, timeIsUp);

//        for (List<Advertisement> a: all) {
//            for (Advertisement aa: a) {
//                System.out.println(aa.getName() + "---------------");
//            }
//            System.out.println("-----------------------------");
//        }
//        System.out.println(all.size());
        return all;
    }

    public void processVideos() throws NoVideoAvailableException{
        List<Advertisement> list = new ArrayList<>();
        for (Advertisement adv: storage.list()) {
            if (adv.getHits() > 0 && adv.getDuration() <= timeSeconds)
                list.add(adv);
        }

        if (list.isEmpty()) {
            throw new NoVideoAvailableException();

        }
//        for (Advertisement a: list) System.out.println(a.getName() + " ~~~~~~~~~~~~~~~~");

        List<Advertisement> best = new ArrayList<>();
        List<List> all = new ArrayList<>();

        long maxSumm = 0;
        int maxTime = 0;
        for (List<Advertisement> a: findAllVar(list, all, best, false)) {
            long maxSumm1 = 0;
            int maxTime1 = 0;
            for (Advertisement b: a) {
                maxSumm1 += b.getAmountPerOneDisplaying();
                maxTime1 += b.getDuration();
            }
            if (maxSumm1 > maxSumm) {
                best = a;
                maxSumm = maxSumm1;
                maxTime = maxTime1;
            } else if (maxSumm1 == maxSumm) {
                if (maxTime1 > maxTime) {
                    best = a;
                    maxTime = maxTime1;
                } else if (maxTime1 == maxTime) {
                    if (a.size() < best.size()) {
                        best = a;
                    }
                }
            }
        }
        Collections.sort(best, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
//                int result = Long.compare(o1.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
//                if (result != 0)
//                    return -result;
//                long oneSecondCost1 = o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration();
//                long oneSecondCost2 = o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration();
//                return Long.compare(oneSecondCost1, oneSecondCost2);
                long a1 = o1.getAmountPerOneDisplaying();
                long a2 = o2.getAmountPerOneDisplaying();
                long a1sec = a1*1000/o1.getDuration();
                long a2sec = a1*1000/o2.getDuration();
                int l = Long.compare(a1,a2);
                if (l != 0) return -l;
                return Long.compare(a1sec, a2sec);
            }
        });

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(best, maxSumm, maxTime));

        for (Advertisement a: best) {
            ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d", a.getName(), a.getAmountPerOneDisplaying(),
                    a.getAmountPerOneDisplaying() * 1000 / a.getDuration()));
            a.revalidate();
        }

    }
}
