package com.javarush.task.task39.task3913;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/logs/"));
//        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
//        System.out.println(logParser.getUniqueIPs(null, new Date()));
//        System.out.println(logParser.getIPsForUser("Vasya Pupkin",null, new Date()));
//        System.out.println(logParser.getIPsForEvent(Event.LOGIN,null, new Date()));
//        System.out.println(logParser.getIPsForStatus(Status.FAILED,null, new Date()));
//        System.out.println(logParser.getDateWhenUserSolvedTask("a", 3, null, null));
//        System.out.println(logParser.execute("get ip"));
//        System.out.println(logParser.execute("get user"));
//        System.out.println(logParser.execute("get date"));
//        System.out.println(logParser.execute("get event"));
//        System.out.println(logParser.execute("get status"));
//        System.out.println(logParser.execute("get event for date = \"30.01.2014 12:56:22\""));
//        System.out.println(logParser.execute("get date for date = \"30.01.2014 12:56:22\""));
//        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));
        System.out.println(logParser.execute("get ip for event = \"LOGIN\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));
        System.out.println(logParser.execute("get ip for status = \"OK\" and date between \"null\" and \"null\""));
//        System.out.println(logParser.execute("get status for = \"\" and date between \"null\" and \"null\""));
        System.out.println(logParser.execute("get date for event = \"DOWNLOAD_PLUGIN\" and date between \"11.12.2012 0:00:00\" and \"03.01.2050 23:59:59\""));
//        System.out.println(logParser.execute("get event for date = \"30.01.2014 12:56:22\" and date between \"30.01.2014 12:56:22\" and "));
//        System.out.println(logParser.execute("get null"));
    }
}