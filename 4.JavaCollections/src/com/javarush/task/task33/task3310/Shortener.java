package com.javarush.task.task33.task3310;


import com.javarush.task.task33.task3310.strategy.StorageStrategy;

public class Shortener {

    private Long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string){

        if (storageStrategy.containsValue(string)) {
            return storageStrategy.getKey(string);
        }
        else {
            lastId ++;
            storageStrategy.put(lastId, string);
            return lastId;
        }
    }

    public synchronized String getString(Long id) {

        if (storageStrategy.containsKey(id)) {
            return storageStrategy.getValue(id);
        }
        else {
            return null;
        }
    }
}

//public class Shortener {
//    private Long lastId = 0L;
//    private StorageStrategy storageStrategy;
//
//    public Shortener(StorageStrategy storageStrategy) {
//        this.storageStrategy = storageStrategy;
//    }
//
//
//    public synchronized Long getId(String string) {
//        if (!storageStrategy.containsValue(string)){
//            lastId++;
//            storageStrategy.put(lastId, string);
//        }
//        return storageStrategy.getKey(string);
//    }
//    public synchronized String getString(Long id) {
//
//        return storageStrategy.getValue(id);
//    }
//}
