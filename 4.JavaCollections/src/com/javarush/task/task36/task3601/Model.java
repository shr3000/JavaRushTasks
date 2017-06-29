package com.javarush.task.task36.task3601;


import java.util.List;

/**
 * Created by MaX on 21.04.2017.
 */
public class Model {
    Service service = new Service();
    public List<String> getStringDataList() {
        return service.getData();
    }
}

/*
public class Shortener {
    private long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string) {
        if (storageStrategy.containsValue(string)) {
            return storageStrategy.getKey(string);
        } else {
            lastId++;
            storageStrategy.put(lastId, string);
            return lastId;
        }
    }
    public synchronized String getString(Long id){
        if (storageStrategy.containsKey(id)) {
            return storageStrategy.getValue(id);
        }
        else {
            return null;
        }
    }
}
public interface StorageStrategy {
    boolean containsKey(Long key);// – должен вернуть true, если хранилище содержит переданный ключ.
    boolean containsValue(String value);// — должен вернуть true, если хранилище содержит переданное значение.
    void put(Long key, String value);// – добавить в хранилище новую пару ключ – значение.
    Long getKey(String value);// – вернуть ключ для переданного значения.
    String getValue(Long key);// – вернуть значение для переданного ключа.
}
 */
