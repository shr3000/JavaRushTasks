package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {
//    Storage storage;
    LRUCache lruCache;
    OriginalRetriever originalRetriever;

    public CachingProxyRetriever(Storage storage) {
//        this.storage = storage;
        lruCache = new LRUCache(40);
        originalRetriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        Object o = lruCache.find(id);
        if (o != null) return o;
        else {
            o = originalRetriever.retrieve(id);
            lruCache.set(id, o);
            return o;
        }
    }
}
