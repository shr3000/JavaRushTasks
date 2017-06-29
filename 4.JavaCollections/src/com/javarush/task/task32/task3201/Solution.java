package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw");
        long cursor = Long.parseLong(args[1]);
        long fileLength = randomAccessFile.length();
        byte [] text = args[2].getBytes();
        if (fileLength - cursor < text.length) {
            randomAccessFile.seek(fileLength);
            randomAccessFile.write(text);
        } else {
            randomAccessFile.seek(cursor);
            randomAccessFile.write(text);
        }
        randomAccessFile.close();
    }
}
