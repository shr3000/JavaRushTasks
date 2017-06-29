package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery{
    private Path logDir;

    public LogParser(Path logDir) {
        this.logDir = logDir;
    }

    @Override
    public Set<Object> execute(String query) {

        Set<Object> set = new HashSet<>();

        String [] strings = query.split(" ");
        if (strings.length == 2) {
            switch (query) {
                case "get ip":
                    for(LogEvent le: getAllLogs())
                            set.add(le.getIP());
                    break;
                case "get user":
                    for(LogEvent le: getAllLogs())
                        set.add(le.getUser());
                    break;
                case "get date":
                    for(LogEvent le: getAllLogs())
                        set.add(le.getDate());
                    break;
                case "get event":
                    for(LogEvent le: getAllLogs())
                        set.add(le.getEvent());
                    break;
                case "get status":
                    for(LogEvent le: getAllLogs())
                        set.add(le.getStatus());
                    break;
            }
        } else {
            Pattern p = Pattern.compile("get (ip|user|date|event|status)"
                    + "( for (ip|user|date|event|status) = \"(.*?)\")?"
                    + "( and date between \"(.*?)\" and \"(.*?)\")?");

            Matcher m = p.matcher(query);
            boolean ma = m.find();
            if (!ma) return set;
            String field1 = m.group(1);
            String field2 = m.group(3);
            String value1 = m.group(4);
            Date dateFrom;
            Date dateTo;

            try {
                dateFrom = new SimpleDateFormat("d.M.yyyy H:m:s").parse(m.group(6));
            } catch (Exception e) {
                dateFrom = null;
            }
            try {
                dateTo = new SimpleDateFormat("d.M.yyyy H:m:s").parse(m.group(7));
            } catch (Exception e) {
                dateTo = null;
            }

            switch (field1) {
                case "ip":
                    set.addAll(getAllIPs(field2, value1, dateFrom, dateTo));
                    break;
                case "user":
                    set.addAll(getAllUsers2(field2, value1, dateFrom, dateTo));
                    break;
                case "date":
                    set.addAll(getAllDates(field2, value1, dateFrom, dateTo));
                    break;
                case "event":
                    set.addAll(getAllEvents2(field2, value1, dateFrom, dateTo));
                    break;
                case "status":
                    set.addAll(getAllStatuses(field2, value1, dateFrom, dateTo));
                    break;
            }
        }

        return set;
    }

    private Set<Event> getAllEvents2(String field2, String value1, Date dateFrom, Date dateTo) {

        Set<Event> set = new HashSet<>();
        for (LogEvent le : getAllLogs()) {
//            if (field2 == null) {
//                set.add(le.getEvent());
//                continue;
//            }
            if (checkDate(le.getDate(), dateFrom, dateTo)) {

                boolean is = false;
                switch (field2) {
                    case "ip":
                        if (le.getIP().equals(value1)) is = true;
                        break;
                    case "user":
                        if (le.getUser().equals(value1)) is = true;
                        break;
                    case "date": {
                        try {
                            Date date = new SimpleDateFormat("d.M.yyyy H:m:s").parse(value1);
                            if (le.getDate().equals(date)) is = true;

                        } catch (ParseException e) {
                        }
                    }
                    break;
                    case "event": if (le.getEvent() == (Event.valueOf(value1))) is = true; break;
                    case "status":
                        if (le.getStatus() == (Status.valueOf(value1))) is = true;
                        break;
                }
                if (is) set.add(le.getEvent());
            }
        }
        return set;
    }

    private Set<String> getAllUsers2(String field2, String value1, Date dateFrom, Date dateTo) {
        Set<String> set = new HashSet<>();
        for (LogEvent le : getAllLogs()) {
//            if (field2 == null) {
//                set.add(le.getUser());
//                continue;
//            }
            if (checkDate(le.getDate(), dateFrom, dateTo)) {

                boolean is = false;
                switch (field2) {
                    case "ip":
                        if (le.getIP().equals(value1)) is = true;
                        break;
                    case "user": if (le.getUser().equals(value1)) is = true; break;
                    case "date": {
                        try {
                            if (le.getDate().equals(new SimpleDateFormat("d.M.yyyy H:m:s").parse(value1))) is = true;
                        } catch (ParseException e) {
                        }
                    }
                    break;
                    case "event":
                        if (le.getEvent() == Event.valueOf(value1)) is = true;
                        break;
                    case "status":
                        if (le.getStatus() == Status.valueOf(value1)) is = true;
                        break;
                }
                if (is) set.add(le.getUser());
            }
        }
        return set;
    }

    private Set<String> getAllIPs(String field2, String value1, Date dateFrom, Date dateTo) {
        Set<String> set = new HashSet<>();
        for (LogEvent le : getAllLogs()) {
//            if (field2 == null) {
//                set.add(le.getIP());
//                continue;
//            }
            if (checkDate(le.getDate(), dateFrom, dateTo)) {

                boolean is = false;
                switch (field2) {
                    case "ip": if (le.getIP().equals(value1)) is = true; break;
                    case "user":
                        if (le.getUser().equals(value1)) is = true;
                        break;
                    case "date": {
                        try {
                            if (le.getDate().equals(new SimpleDateFormat("d.M.yyyy H:m:s").parse(value1))) is = true;
                        } catch (ParseException e) {
                        }
                    }
                    break;
                    case "event": {
                        if (le.getEvent().name().equals(value1)) is = true;
//                        if (le.getEvent() == Event.valueOf(value1)) is = true;
                    }
                    break;
                    case "status":
                        if (le.getStatus() == Status.valueOf(value1)) is = true;
                        break;
                }
                if (is) set.add(le.getIP());
            }
        }
        return set;
    }

    public Set<Date> getAllDates(String field2, String value1, Date dateFrom, Date dateTo) {
        Set<Date> set = new HashSet<>();
        for (LogEvent le : getAllLogs()) {
//            if (field2 == null) {
//                set.add(le.getDate());
//                continue;
//            }
            if (checkDate(le.getDate(), dateFrom, dateTo)) {

                boolean is = false;
                switch (field2) {
                    case "ip":
                        if (le.getIP().equals(value1)) is = true;
                        break;
                    case "user":
                        if (le.getUser().equals(value1)) is = true;
                        break;
                    case "date": {
                        try{
                            if (le.getDate().equals(new SimpleDateFormat("d.M.yyyy H:m:s").parse(value1))) is = true;
                        } catch (ParseException e){}
                    } break;
                    case "event":
                        if (le.getEvent() == Event.valueOf(value1)) is = true;
                        break;
                    case "status":
                        if (le.getStatus() == Status.valueOf(value1)) is = true;
                        break;
                }
                if (is) set.add(le.getDate());
            }
        }
        return set;
    }

    public Set<Status> getAllStatuses(String field2, String value1, Date dateFrom, Date dateTo) {
        Set<Status> set = new HashSet<>();
        for (LogEvent le : getAllLogs()) {
            if (field2 == null) {
                set.add(le.getStatus());
                continue;
            }
            if (checkDate(le.getDate(), dateFrom, dateTo)) {

                boolean is = false;
                switch (field2) {
                    case "ip":
                        if (le.getIP().equals(value1)) is = true;
                        break;
                    case "user":
                        if (le.getUser().equals(value1)) is = true;
                        break;
                    case "date": {
                        try {
                            if (le.getDate().equals(new SimpleDateFormat("d.M.yyyy H:m:s").parse(value1))) is = true;
                        } catch (ParseException e) {
                        }
                    }
                    break;
                    case "event":
                        if (le.getEvent() == Event.valueOf(value1)) is = true;
                        break;
                    case "status": if (le.getStatus() == Status.valueOf(value1)) is = true; break;
                }
                if (is) set.add(le.getStatus());
            }
        }
        return set;
    }


    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (checkDate(le.getDate(), after, before))
                set.add(le.getEvent());
        return set;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getIP().equals(ip) && checkDate(le.getDate(), after, before))
                set.add(le.getEvent());
        return set;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getEvent());
        return set;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getStatus() == Status.FAILED && checkDate(le.getDate(), after, before))
                set.add(le.getEvent());
        return set;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getStatus() == Status.ERROR && checkDate(le.getDate(), after, before))
                set.add(le.getEvent());
        return set;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int i = 0;
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.SOLVE_TASK && le.eventTaskNumber == task
                    && checkDate(le.getDate(), after, before))
                i++;
        return i;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        int i = 0;
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.DONE_TASK && le.eventTaskNumber == task
                    && checkDate(le.getDate(), after, before))
                i++;
        return i;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.SOLVE_TASK && checkDate(le.getDate(), after, before)) {
                if (map.containsKey(le.getEventTaskNumber()))
                    map.put(le.getEventTaskNumber(), map.get(le.getEventTaskNumber()) + 1);
                else map.put(le.getEventTaskNumber(), 1);
            }
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.DONE_TASK && checkDate(le.getDate(), after, before)) {
                if (map.containsKey(le.getEventTaskNumber()))
                    map.put(le.getEventTaskNumber(), map.get(le.getEventTaskNumber()) + 1);
                else map.put(le.getEventTaskNumber(), 1);
            }
        return map;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getUser().equals(user) && le.getEvent() == event &&
                    checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getStatus() == Status.FAILED && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getStatus() == Status.ERROR && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        TreeSet<Date> set = new TreeSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.LOGIN && le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set.size() > 0 ? set.first(): null;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        TreeSet<Date> set = new TreeSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.SOLVE_TASK && le.getEventTaskNumber() == task
                    && le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set.size() > 0 ? set.first(): null;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        TreeSet<Date> set = new TreeSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.DONE_TASK && le.getEventTaskNumber() == task
                    && le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set.size() > 0 ? set.first(): null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.WRITE_MESSAGE && le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set<Date> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getEvent() == Event.DOWNLOAD_PLUGIN && le.getUser().equals(user) && checkDate(le.getDate(), after, before))
                set.add(le.getDate());
        return set;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            set.add(le.getUser());
        return set;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if (le.getUser().equals(user) && checkDate(le.getDate(), after, before)) set.add(le.getEvent());
        return set.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getIP().equals(ip) && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.LOGIN && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.DOWNLOAD_PLUGIN && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.WRITE_MESSAGE && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.SOLVE_TASK && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.SOLVE_TASK && checkDate(le.getDate(), after, before)
                    && le.getEventTaskNumber() == task) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.DONE_TASK && checkDate(le.getDate(), after, before)) set.add(le.getUser());
        return set;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs())
            if(le.getEvent() == Event.DONE_TASK && checkDate(le.getDate(), after, before)
                    && le.getEventTaskNumber() == task) set.add(le.getUser());
        return set;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs()){
            if (checkDate(le.getDate(), after, before))
                set.add(le.getIP());
        }
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs()){
            if (checkDate(le.getDate(), after, before) && le.getUser().equals(user)){
                set.add(le.getIP());
            }
        }
        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs()){
            if (checkDate(le.getDate(), after, before) && le.getEvent() == event){
                set.add(le.getIP());
            }
        }
        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for(LogEvent le: getAllLogs()){
            if (checkDate(le.getDate(), after, before) && le.getStatus() == status){
                set.add(le.getIP());
            }
        }
        return set;
    }

    private boolean checkDate(Date currentDate, Date after, Date before){
        return (after == null || currentDate.getTime() >= after.getTime())
                && (before == null || currentDate.getTime() <= before.getTime());
    }

    private List<LogEvent> getAllLogs(){

        List<LogEvent> logEvents = new ArrayList<>();

        File file = new File(logDir.toString());
        File[] listFiles = file.listFiles(pathname -> pathname.getName().endsWith(".log"));
        if (listFiles.length == 0) return logEvents;
        List<String> stringList = new ArrayList<>();
        for (File f: listFiles) {
            try {
                List<String> list = Files.readAllLines(f.toPath());
                stringList.addAll(list);

            } catch (IOException e) {
            }
        }
        for (String s: stringList){
            logEvents.add(new LogEvent(s));
        }

        return logEvents;
    }

    class LogEvent{
        private String IP;
        private String user;
        private Date date;
        private Event event;
        private int eventTaskNumber;
        private Status status;

        public LogEvent(String s) {
            String[] strings = s.split("\\t");
            IP = strings[0];
            user = strings[1];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy H:m:s");
            try{
                date = simpleDateFormat.parse(strings[2]);
            }catch (ParseException e){}
            String[] str = strings[3].split("\\s");
            event = Event.valueOf(str[0]);
            if (str.length == 2) eventTaskNumber = Integer.parseInt(str[1]);
            status = Status.valueOf(strings[4]);
        }

        public String getIP() {
            return IP;
        }

        public String getUser() {
            return user;
        }

        public Date getDate() {
            return date;
        }

        public Event getEvent() {
            return event;
        }

        public Status getStatus() {
            return status;
        }

        public int getEventTaskNumber() {
            return eventTaskNumber;
        }
    }
}