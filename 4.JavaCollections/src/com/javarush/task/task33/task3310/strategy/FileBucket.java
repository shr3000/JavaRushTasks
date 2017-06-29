package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {

        try {
            path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize() { //, он должен возвращать размер файла на который указывает path.

        try {
            return Files.size(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        return 0;
    }

    public void putEntry(Entry entry) {// — должен сериализовывать переданный entry в файл.
        // Учти, каждый entry может содержать еще один entry.
//        for (Entry e = entry; e != null; e = e.next) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
                oos.writeObject(entry);
                oos.flush();
                oos.close();
            } catch (IOException ex) {
                ExceptionHandler.log(ex);
            }
//        }
    }

    public Entry getEntry() {// — должен забирать entry из файла. Если файл имеет нулевой размер, вернуть null.
        if(getFileSize() == 0)
            return null;
        Entry entry = null;
        try {
            ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path));
            entry = (Entry) in.readObject();
            in.close();
        }catch (
//                IOException|ClassNotFound
                        Exception ex){
            ExceptionHandler.log(ex);
        }
        return entry;
    }

    public void remove() {// – удалять файл на который указывает path.
        try{
            Files.delete(path);
        }catch (IOException e){
            ExceptionHandler.log(e);
        }
    }
}
